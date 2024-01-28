package code.shubham.core.documentstore.web.v1.controllers;

import code.shubham.commons.contexts.AccountIDContextHolder;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.core.blobstore.services.BlobService;
import code.shubham.core.documentstore.dao.entities.Document;
import code.shubham.core.documentstore.services.DocumentService;
import code.shubham.core.documentstore.web.v1.validators.SaveDocumentRequestValidator;
import code.shubham.core.documentstoremodels.SaveDocumentRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.Map;

@RestController
@RequestMapping("/v1/documents")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Document Store")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
@RequiredArgsConstructor
public class DocumentStoreController {

	@Value("${documents.bucket.name}")
	private String defaultBucket;

	private final BlobService blobService;

	private final DocumentService service;

	@GetMapping("/uploadURL")
	public ResponseEntity<?> getDocumentUploadURL(@RequestBody(required = false) final Map<String, String> metadata) {
		return ResponseUtils.getDataResponseEntity(200,
				this.blobService.getPreSignedUploadUrl(AccountIDContextHolder.get().toString(), this.defaultBucket,
						AccountIDContextHolder.get().toString(), metadata));
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody final SaveDocumentRequest request) {
		new SaveDocumentRequestValidator().validateOrThrowException(request);

		Utils.validateAccountOrThrowException(request.getUserId());

		return ResponseUtils.getDataResponseEntity(HttpStatusCode.CREATED,
				this.service.save(Document.builder()
					.owner(request.getUserId())
					.name(request.getName())
					.blobId(request.getBlobId())
					.build()));
	}

}
