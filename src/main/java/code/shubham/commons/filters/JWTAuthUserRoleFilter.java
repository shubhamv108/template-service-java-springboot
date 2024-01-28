package code.shubham.commons.filters;

import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.contexts.AccountContextHolder;
import code.shubham.commons.contexts.AccountIDContextHolder;
import code.shubham.core.iamcommons.IAccountService;
import code.shubham.core.iammodels.GetOrCreateAccount;
import code.shubham.core.iammodels.GetAccountResponse;
import code.shubham.core.iammodels.AccountDTO;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;

@Slf4j
@Component
@Order(8)
public class JWTAuthUserRoleFilter implements Filter {

	private final IAccountService userService;

	@Autowired
	public JWTAuthUserRoleFilter(final IAccountService userService) {
		this.userService = userService;
	}

	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
			final FilterChain chain) throws java.io.IOException, ServletException {
		try {
			AccountDTO user = null;
			if (AccountIDContextHolder.get() == null)
				user = this.getUserFromSecurityContext();

			if (user != null) {
				AccountContextHolder.set(user);
				AccountIDContextHolder.set(user.id());
			}

			log.info(String.format("Requesting user: %s", AccountIDContextHolder.get()));

			chain.doFilter(servletRequest, servletResponse);
		}
		finally {
			AccountIDContextHolder.clear();
			AccountContextHolder.clear();
			RoleContextHolder.clear();
		}
	}

	private AccountDTO getUserFromSecurityContext() {
		if (!(SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken))
			return null;
		return this
			.currentUser(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaims());
	}

	private AccountDTO currentUser(final Map<String, Object> claims) {
		final String email = claims.get("email").toString();
		final String name = claims.get("name").toString();
		final GetAccountResponse response = this.userService
			.getOrCreate(GetOrCreateAccount.Request.builder().email(email).name(name).build());
		RoleContextHolder.set(new HashSet<>(response.getRoles()));
		return response.getAccount();
	}

}
