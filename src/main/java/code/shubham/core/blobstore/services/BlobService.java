package code.shubham.core.blobstore.services;

import code.shubham.commons.aws.S3Utils;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.core.blobservicecommons.IBlobService;
import code.shubham.core.blobstore.dao.entities.Blob;
import code.shubham.core.blobstore.dao.repositories.BlobRepository;
import code.shubham.core.blobstoremodels.BlobResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlobService implements IBlobService {

	private final BlobRepository repository;

	@Value("${aws.default.region}")
	private String defaultRegion;

	@Override
	public BlobResponse getPreSignedUploadUrl(final String owner, final String bucket, final String key,
			Map<String, String> metadata) {
		if (metadata == null)
			metadata = new HashMap<>();

		final Blob blob = this.repository.save(Blob.builder()
			.bucket(bucket)
			.keyName(key)
			.owner(owner)
			.checksum(Optional.ofNullable(metadata).map(meta -> meta.get("checksum")).orElse(null))
			.build());
		metadata.remove("checksum");
		return BlobResponse.builder()
			.blobId(blob.getId())
			.url(S3Utils.createPresignedUrl(this.defaultRegion, blob.getBucket(), blob.getFullKey(), metadata))
			.build();
	}

	@Override
	public Boolean doesBlobExist(final Long id, final String owner) {
		return this.get(id, owner).isPresent();
	}

	@Override
	public BlobResponse getPreSignedDownloadUrl(final Long id, final String owner) {
		final Blob blob = this.getOrThrowException(id, owner);
		return BlobResponse.builder()
			.blobId(blob.getId())
			.url(S3Utils.createPresignedGetUrl(this.defaultRegion, blob.getBucket(), blob.getFullKey(), 600000))
			.build();
	}

	@Override
	public Blob getOrThrowException(final Long id, final String owner) {
		return this.get(id, owner)
			.orElseThrow(() -> new InvalidRequestException("id", "No such blob exists with id: %s", id.toString()));
	}

	@Override
	public Optional<Blob> get(final Long id, final String owner) {
		return this.repository.findByIdAndOwner(id, owner)
			.filter(blob -> S3Utils.doesObjectExist(this.defaultRegion, blob.getBucket(), blob.getFullKey(),
					blob.getChecksum()));
	}

}
