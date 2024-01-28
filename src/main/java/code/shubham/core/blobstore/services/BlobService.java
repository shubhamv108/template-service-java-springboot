package code.shubham.core.blobstore.services;

import code.shubham.commons.aws.S3Utils;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.core.blobstore.dao.entities.Blob;
import code.shubham.core.blobstore.dao.repositories.BlobRepository;
import code.shubham.core.blobstoremodels.BlobResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlobService {

	private final BlobRepository repository;

	@Value("${aws.default.region}")
	private String defaultRegion;

	public BlobResponse getPreSignedUploadUrl(final String owner, final String bucket, final String key,
			final Map<String, String> metadata) {
		final Blob blob = this.repository.save(Blob.builder()
			.bucket(bucket)
			.keyName(key)
			.owner(owner)
			.checksum(Optional.ofNullable(metadata).map(meta -> meta.get("checksum")).orElse(null))
			.build());
		metadata.remove("checksum");
		return BlobResponse.builder()
			.blobId(blob.getId())
			.uploadUrl(S3Utils.createPresignedUrl(this.defaultRegion, blob.getBucket(), blob.getFullKey(), metadata))
			.build();
	}

	public Boolean doesBlobExist(final Long id, final String owner) {
		return this.get(id, owner).map(blob -> true).orElse(false);
	}

	public Optional<Blob> get(final Long id, final String owner) {
		return this.repository.findByIdAndOwner(id, owner)
			.filter(blob -> S3Utils.doesObjectExist(this.defaultRegion, blob.getBucket(), blob.getFullKey(),
					blob.getChecksum()));
	}

	public BlobResponse getPreSignedDownloadUrl(final Long id, final String owner) {
		final Blob blob = this.get(id, owner)
			.orElseThrow(() -> new InvalidRequestException("id", "No such blob exists with id: %s", id.toString()));
		return BlobResponse.builder()
			.blobId(blob.getId())
			.uploadUrl(S3Utils.createPresignedGetUrl(this.defaultRegion, blob.getBucket(), blob.getFullKey()))
			.build();
	}

}
