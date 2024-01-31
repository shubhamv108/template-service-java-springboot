package code.shubham.core.document.services;

import code.shubham.commons.contexts.AccountIDContextHolder;
import code.shubham.commons.enums.DownloadURLSource;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.core.blobstore.dao.entities.Blob;
import code.shubham.core.blobstore.services.BlobService;
import code.shubham.core.blobstoremodels.BlobResponse;
import code.shubham.core.document.dao.entities.Document;
import code.shubham.core.document.dao.repositories.DocumentRepository;
import code.shubham.core.document.services.factories.DocumentDownloadURLFactory;
import code.shubham.core.documentmodels.DocumentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

	@Value("${aws.default.region}")
	private String defaultRegion;

	@Value("${documents.download.url.source}")
	private DownloadURLSource downloadURLSource;

	@Value("${documents.bucket.name}")
	private String defaultBucket;

	private final DocumentRepository repository;

	private final BlobService blobService;

	private final DocumentDownloadURLFactory downloadURLFactory;

	public BlobResponse getUploadURL(final Map<String, String> metadata) {
		return this.blobService.getPreSignedUploadUrl(AccountIDContextHolder.get().toString(), this.defaultBucket,
				AccountIDContextHolder.get().toString(), metadata);
	}

	public DocumentDTO save(final Document document) {
		if (!this.blobService.doesBlobExist(document.getBlobId(), document.getOwner().toString()))
			throw new InvalidRequestException("blobId", "blob does not exist");

		final Optional<Document> existing = this.repository.findByOwnerAndName(document.getOwner(), document.getName());
		if (existing.isPresent()) {
			existing.get().setBlobId(document.getBlobId());
			return this.convert(this.repository.save(existing.get()));
		}
		return this.convert(this.repository.save(document));
	}

	public String getDownloadURL(final Long id) {
		return this.repository.findById(id)
			.map(document -> this.blobService.get(document.getBlobId(), document.getOwner()))
			.filter(Optional::isPresent)
			.map(Optional::get)
			.map(Blob::getFullKey)
			.map(key -> this.downloadURLFactory.get(this.downloadURLSource, key))
			.orElseThrow(() -> new InvalidRequestException("id", "No document found with id: %s", id.toString()));

	}

	private DocumentDTO convert(final Document document) {
		return DocumentDTO.builder()
			.documentId(document.getId())
			.name(document.getName())
			.owner(document.getOwner())
			.build();
	}

}
