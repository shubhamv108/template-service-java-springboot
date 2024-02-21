package code.shubham.core.blobservicecommons;

import code.shubham.core.blobstore.dao.entities.Blob;
import code.shubham.core.blobstoremodels.BlobResponse;

import java.util.Map;
import java.util.Optional;

public interface IBlobService {

	BlobResponse getPreSignedUploadUrl(String owner, String bucket, String key, Map<String, String> metadata);

	Boolean doesBlobExist(Long id, String owner);

	BlobResponse getPreSignedDownloadUrl(Long id, String owner);

	Blob getOrThrowException(Long id, String owner);

	Optional<Blob> get(Long id, String owner);

}
