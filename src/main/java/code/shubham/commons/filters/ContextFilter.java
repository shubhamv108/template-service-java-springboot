package code.shubham.commons.filters;

import code.shubham.commons.contexts.TenantIDContextHolder;
import code.shubham.commons.contexts.AccountContextHolder;
import code.shubham.commons.contexts.AccountIDContextHolder;
import code.shubham.core.iam.services.AccountService;
import code.shubham.core.iammodels.AccountDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@Order(7)
public class ContextFilter implements Filter {

	private final AccountService accountService;

	@Autowired
	public ContextFilter(final AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
			final FilterChain chain) throws java.io.IOException, ServletException {
		try {
			final HttpServletRequest request = (HttpServletRequest) servletRequest;
			final String userId = Optional.ofNullable(request.getHeader("userId"))
				.orElse((String) request.getAttribute("userId"));
			if (userId != null)
				AccountIDContextHolder.set(Long.valueOf(userId));

			final String tenantId = request.getHeader("tenantId");
			if (tenantId != null)
				TenantIDContextHolder.set(tenantId);

			final String userEmail = Optional.ofNullable(request.getHeader("userEmail"))
				.orElse((String) request.getAttribute("userEmail"));
			if (userEmail != null)
				AccountContextHolder.set(new AccountDTO(Long.valueOf(userId), userEmail));

			chain.doFilter(servletRequest, servletResponse);
		}
		finally {
			AccountContextHolder.clear();
			AccountIDContextHolder.clear();
			TenantIDContextHolder.clear();
		}
	}

}
