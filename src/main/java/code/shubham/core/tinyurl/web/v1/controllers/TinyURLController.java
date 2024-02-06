package code.shubham.core.tinyurl.web.v1.controllers;

import code.shubham.commons.contexts.AccountIDContextHolder;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.exceptions.NotFoundException;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.StringUtils;
import code.shubham.core.tinyurl.dao.entities.ShortURL;
import code.shubham.core.tinyurl.services.TinyURLService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/v1/tinyurl")
@RequiredArgsConstructor
public class TinyURLController {

	private final TinyURLService service;

	@GetMapping
	public ResponseEntity<?> get(@RequestParam("url") final String url) {

		if (StringUtils.isEmpty(url))
			throw new InvalidRequestException("url", "url must not be null or empty");

		final ShortURL shortURL = ShortURL.builder().url(url).accountId(AccountIDContextHolder.get()).build();
		final String tinyURL = this.service.create(shortURL);
		return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED.value(), new HashMap<>() {
			{
				put("url", tinyURL);
			}
		});
	}

	@GetMapping("/{tinyURL}")
	public ResponseEntity<?> redirect(@PathVariable("tinyURL") final String tinyURL) {

		if (StringUtils.isEmpty(tinyURL))
			throw new InvalidRequestException("tinyURL", "tinyURL must not be null or empty");

		return this.service.resolve(tinyURL, AccountIDContextHolder.get())
			.map(ResponseUtils::redirect)
			.orElseThrow(() -> new NotFoundException("tinURL", "tinyURL %s not found", tinyURL));
	}

}
