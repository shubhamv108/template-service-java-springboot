package code.shubham.core.blobstore.services;

import code.shubham.commons.aws.S3Utils;
import code.shubham.core.blobstore.dao.entities.Blob;
import code.shubham.core.blobstore.dao.repositories.BlobRepository;
import code.shubham.core.blobstoremodels.CreateBlobResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlobService {

	private final BlobRepository repository;

	@Value("${aws.default.region}")
	private String defaultRegion;

	public CreateBlobResponse getPreSignedUploadUrl(final String owner, final String bucket, final String key,
			final Map<String, String> metadata) {
		final Blob blob = this.repository.save(Blob.builder().bucket(bucket).keyName(key).owner(owner).build());
		return CreateBlobResponse.builder()
			.blobId(blob.getId())
			.uploadUrl(S3Utils.createPresignedUrl(this.defaultRegion, blob.getBucket(), blob.getFullKey(), metadata))
			.build();
	}

	public Boolean doesBlobExist(final Long id, final String owner) {
		return this.repository.findByIdAndOwner(id, owner)
			.map(blob -> true)
			// S3Utils.doesObjectExist(this.defaultRegion, blob.getBucket(),
			// blob.getFullKey()))
			.orElse(false);
	}

}
