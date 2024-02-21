package code.shubham.core.pastebin.proxies;

import code.shubham.core.blobservicecommons.IBlobService;
import code.shubham.core.blobstore.dao.entities.Blob;
import code.shubham.core.blobstoremodels.BlobResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasteBinBlobServiceProxy implements IBlobService {

	private final IBlobService service;

	@Override
	public BlobResponse getPreSignedUploadUrl(String owner, String bucket, String key, Map<String, String> metadata) {
		return this.service.getPreSignedUploadUrl(owner, bucket, key, metadata);
	}

	@Override
	public Boolean doesBlobExist(final Long id, final String owner) {
		return this.service.doesBlobExist(id, owner);
	}

	@Override
	public BlobResponse getPreSignedDownloadUrl(Long id, String owner) {
		return this.service.getPreSignedDownloadUrl(id, owner);
	}

	@Override
	public Blob getOrThrowException(Long id, String owner) {
		return this.service.getOrThrowException(id, owner);
	}

	@Override
	public Optional<Blob> get(Long id, String owner) {
		return this.service.get(id, owner);
	}

}
