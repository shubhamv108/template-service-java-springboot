package code.shubham.core.pastebin.services;

import code.shubham.commons.contexts.AccountIDContextHolder;
import code.shubham.commons.enums.DownloadURLSource;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.factories.DownloadURLFactory;
import code.shubham.commons.utils.StringUtils;
import code.shubham.core.blobservicecommons.IBlobService;
import code.shubham.core.blobstore.dao.entities.Blob;
import code.shubham.core.blobstoremodels.BlobResponse;
import code.shubham.core.pastebin.dao.entities.Paste;
import code.shubham.core.pastebin.dao.repositories.PasteRepository;
import code.shubham.core.pastebinmodels.CreatePasteRequest;
import code.shubham.core.pastebinmodels.CreatePasteResponse;
import code.shubham.core.tinyurlcommons.ITinyURLService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasteService {

	@Value("${pastebin.download.url.source}")
	private DownloadURLSource downloadURLSource;

	@Value("${pastebin.bucket.name}")
	private String defaultBucket;

	private final PasteRepository repository;

	private final ITinyURLService tinyURLService;

	private final IBlobService blobService;

	public BlobResponse getUploadURL(final Map<String, String> metadata) {
		return this.blobService.getPreSignedUploadUrl(AccountIDContextHolder.get().toString(), this.defaultBucket,
				AccountIDContextHolder.get().toString(), metadata);
	}

	@Autowired
	@Qualifier("PasteBinDownloadURLFactory")
	private DownloadURLFactory downloadURLFactory;

	@Transactional
	public CreatePasteResponse create(final CreatePasteRequest request, final Long accountId) {
		final String alias = this.tinyURLService.create(null, request.getAlias(), request.getTtlInSeconds());
		if (!this.blobService.doesBlobExist(request.getBlobId(), accountId.toString()))
			throw new InvalidRequestException("blobId", "blob with id: %s does not exist",
					request.getBlobId().toString());

		final Paste paste = Paste.builder()
			.keyName(alias)
			.blobId(request.getBlobId())
			.accountId(accountId)
			.expiryAt(new Date(System.currentTimeMillis() + (request.getTtlInSeconds() * 1000)))
			.build();
		final Paste persisted = this.repository.save(paste);
		return CreatePasteResponse.builder()
			.shortUrl(persisted.getKeyName())
			.uploadUrlExpiryAt(persisted.getExpiryAt())
			.build();
	}

	public String getDownloadURL(final String key, final Long accountId) {
		if (StringUtils.isEmpty(key))
			throw new InvalidRequestException("key", "key must not null or empty");

		return this.repository.findByKeyNameAndAccountId(key, accountId)
			.map(paste -> this.blobService.get(paste.getBlobId(), paste.getAccountId().toString()))
			.filter(Optional::isPresent)
			.map(Optional::get)
			.map(Blob::getFullKey)
			.map(blobKey -> this.downloadURLFactory.get(this.downloadURLSource, blobKey))
			.orElseThrow(() -> new InvalidRequestException("key", "No paste found for key: %s", key));
	}

}
