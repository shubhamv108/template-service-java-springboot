package code.shubham.commons.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@Component
@Order(6)
public class ContextFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain chain) throws java.io.IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String userIdString = java.util.Optional.ofNullable(request.getHeader("userId"))
                .orElse((String) request.getAttribute("userId"));
        if (userIdString != null)
            code.shubham.commons.contexts.UserContextHolder.setUserId(Integer.valueOf(userIdString));

        String tenantId = request.getHeader("tenantId");
        if (tenantId != null)
            code.shubham.commons.contexts.TenantContextHolder.setTenant(tenantId);

        chain.doFilter(servletRequest, servletResponse);

        code.shubham.commons.contexts.UserContextHolder.clear();
        code.shubham.commons.contexts.TenantContextHolder.clear();
    }
}
