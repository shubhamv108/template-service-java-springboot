package code.shubham.core.iam.web.v1.controllers;

import code.shubham.commons.contexts.AccountIDContextHolder;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.core.iam.services.AccountService;
import code.shubham.core.iammodels.GetOrCreateAccount;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Account")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;

	@GetMapping
	public ResponseEntity<?> getByContextId() {
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK,
				this.accountService.getById(AccountIDContextHolder.get()));
	}

	@GetMapping("/{accountId}")
	public ResponseEntity<?> getById(@PathVariable("accountId") final Long accountId) {
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK, this.accountService.getById(accountId));
	}

}
