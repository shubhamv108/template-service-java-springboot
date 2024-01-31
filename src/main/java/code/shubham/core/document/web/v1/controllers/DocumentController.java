package code.shubham.core.document.web.v1.controllers;

import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.core.document.dao.entities.Document;
import code.shubham.core.document.services.DocumentService;
import code.shubham.core.document.web.v1.validators.SaveDocumentRequestValidator;
import code.shubham.core.documentmodels.SaveDocumentRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.http.HttpStatusCode;

import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("/v1/documents")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Document Store")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
@RequiredArgsConstructor
public class DocumentController {

	private final DocumentService service;

	@GetMapping("/uploadURL")
	public ResponseEntity<?> getDocumentUploadURL(@RequestBody(required = false) final Map<String, String> metadata) {
		return ResponseUtils.getDataResponseEntity(200, this.service.getUploadURL(metadata));
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody final SaveDocumentRequest request) {
		new SaveDocumentRequestValidator().validateOrThrowException(request);

		Utils.validateAccountOrThrowException(request.getAccountId());

		return ResponseUtils.getDataResponseEntity(HttpStatusCode.CREATED,
				this.service.save(Document.builder()
					.owner(request.getAccountId().toString())
					.name(request.getName())
					.blobId(request.getBlobId())
					.build()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getDownloadURL(@PathVariable("id") final Long id) throws URISyntaxException {
		return ResponseUtils.redirect(this.service.getDownloadURL(id));
	}
}
