package code.shubham.core.iam.web.v1.controllers;

import code.shubham.commons.utils.ResponseUtils;
import code.shubham.core.iam.services.UserService;
import code.shubham.core.iammodels.GetOrCreateUser;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "User")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping
	public ResponseEntity<?> getOrCreateUser(@RequestBody GetOrCreateUser.Request request) {
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK, this.userService.getOrCreate(request));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> getById(@PathVariable("userId") final Long userId) {
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK, this.userService.getById(userId));
	}

}
