package code.shubham.core.accountprofiles.web.v1.controllers;

import code.shubham.commons.contexts.AccountIDContextHolder;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.core.accountprofiles.services.AccountProfileService;
import code.shubham.core.accountprofiles.web.v1.validators.UpdateUserProfileRequestValidator;
import code.shubham.core.accountprofilemodels.UpdateUserProfileRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts/profiles")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "User Profiles")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
@RequiredArgsConstructor
public class AccountProfileController {

	private final AccountProfileService service;

	@Operation(description = "PUT Profile Information",
			summary = "Idempotent endpoint for setting user profile information like address etc",
			responses = { @ApiResponse(description = "Success", responseCode = "200"),
					@ApiResponse(description = "Unauthenticated", responseCode = "401") })
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody final UpdateUserProfileRequest request) {
		new UpdateUserProfileRequestValidator().validateOrThrowException(request);
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK,
				this.service.update(AccountIDContextHolder.get(), request.getAddress()));
	}

	@GetMapping
	public ResponseEntity<?> getByAccountId(@RequestParam("accountId") final Long accountId) {
		Utils.validateAccountOrThrowException(accountId);
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK, this.service.fetchByAccountId(accountId));
	}

}
