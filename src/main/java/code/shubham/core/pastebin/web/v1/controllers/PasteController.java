package code.shubham.core.pastebin.web.v1.controllers;

import code.shubham.commons.contexts.AccountIDContextHolder;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.core.pastebin.services.PasteService;
import code.shubham.core.pastebinmodels.CreatePasteRequest;
import code.shubham.core.pastebinmodels.CreatePasteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/paste")
@RequiredArgsConstructor
public class PasteController {

	private final PasteService service;

	@GetMapping("/uploadURL")
	public ResponseEntity<?> getDocumentUploadURL(@RequestBody(required = false) final Map<String, String> metadata) {
		return ResponseUtils.getDataResponseEntity(200, this.service.getUploadURL(metadata));
	}

	@PostMapping
	public CreatePasteResponse create(@RequestBody final CreatePasteRequest request) {
		return this.service.create(request, AccountIDContextHolder.get());
	}

	@GetMapping("/{alias}")
	public ResponseEntity<?> getDownloadURL(@PathVariable("alias") final String alias) {
		return ResponseUtils.redirect(this.service.getDownloadURL(alias, AccountIDContextHolder.get()));
	}

}
