package code.shubham.core.lock.web.v1.controllers;

import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.StringUtils;
import code.shubham.core.lock.services.LockService;
import code.shubham.core.lock.web.v1.validators.LockRequestValidator;
import code.shubham.core.lock.web.v1.validators.UnlockRequestValidator;
import code.shubham.core.lockmodels.LockDTO;
import code.shubham.core.lockmodels.LockRequestDTO;
import code.shubham.core.lockmodels.UnlockRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/locks")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Lock")
public class LockController {

	private final LockService service;

	@Autowired
	public LockController(final LockService service) {
		this.service = service;
	}

	@GetMapping("/{name}")
	public ResponseEntity<?> getByName(@PathVariable @NotNull @NotEmpty final String name) {
		this.validateNameOrThrowException(name);

		return ResponseUtils.getDataResponseEntity(HttpStatus.OK.value(),
				this.service.fetchByName(name)
					.map(lock -> LockDTO.builder().version(lock.getVersion()).build())
					.orElseThrow(() -> new InvalidRequestException("name", "no lock for name", name)));

	}

	@PutMapping("/{name}")
	public ResponseEntity<?> lock(@PathVariable @NotNull @NotEmpty final String name,
			@RequestBody final LockRequestDTO request) {
		this.validateNameOrThrowException(name);
		new LockRequestValidator().validateOrThrowException(request);

		if (this.service.lock(name, request.getPreviousVersion(), request.getOwner(), request.getTimeToLiveInSeconds()))
			return ResponseUtils.getResponseEntity(HttpStatus.OK.value());

		return ResponseUtils.getErrorResponseEntity(HttpStatus.LOCKED.value(),
				String.format("%s already locked", name));
	}

	@PatchMapping("/{name}")
	public ResponseEntity<?> renew(@PathVariable @NotNull @NotEmpty final String name,
			@RequestBody final LockRequestDTO request) {
		this.validateNameOrThrowException(name);
		new LockRequestValidator().validateOrThrowException(request);

		if (this.service.renew(name, request.getPreviousVersion(), request.getOwner(),
				request.getTimeToLiveInSeconds()))
			return ResponseUtils.getResponseEntity(HttpStatus.OK.value());
		return ResponseUtils.getErrorResponseEntity(HttpStatus.NOT_FOUND.value(),
				String.format("Lock not found on name: %s for owner: %s with version: %d", name, request.getOwner(),
						request.getPreviousVersion()));
	}

	@DeleteMapping("/{name}")
	public ResponseEntity<?> unlock(@PathVariable @NotNull @NotEmpty final String name,
			@RequestBody final UnlockRequestDTO request) {
		this.validateNameOrThrowException(name);
		new UnlockRequestValidator().validateOrThrowException(request);

		if (this.service.unlock(name, request.getOwner(), request.getVersion()))
			return ResponseUtils.getResponseEntity(HttpStatus.OK.value());
		return ResponseUtils.getErrorResponseEntity(HttpStatus.NOT_FOUND.value(),
				String.format("Lock not found on name: %s for owner: %s with version: %d", name, request.getOwner(),
						request.getVersion()));
	}

	private void validateNameOrThrowException(final String name) {
		if (StringUtils.isEmpty(name))
			throw new InvalidRequestException("name", "must not be empty", "name");
	}

}