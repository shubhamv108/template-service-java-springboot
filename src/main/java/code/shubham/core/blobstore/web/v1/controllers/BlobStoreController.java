package code.shubham.core.blobstore.web.v1.controllers;

import code.shubham.core.blobstore.services.BlobService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/blobs")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Blob Store")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
@RequiredArgsConstructor
public class BlobStoreController {

	private final BlobService blobService;

	// @GetMapping("/uploadURL")
	// public ResponseEntity<?> getDocumentUploadURL(@RequestBody(required = false) final
	// Map<String, String> request) {
	// return ResponseUtils.getDataResponseEntity(200, this.blobService
	// .getPreSignedUploadUrl(UserIDContextHolder.get().toString(), this.defaultBucket,
	// UserIDContextHolder.get().toString(), request));
	// }

}
