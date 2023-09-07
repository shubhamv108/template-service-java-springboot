package code.shubham.commons.filters;

import code.shubham.commons.Constants;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(2)
public class RequestTimestampFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws java.io.IOException, ServletException {
		Long now = System.currentTimeMillis();

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		request.setAttribute(Constants.RequestKey.REQUEST_START_TIMESTAMP, now);
		Object requestId = request.getAttribute(Constants.RequestKey.REQUEST_ID);
		if (requestId == null) {
			requestId = java.util.UUID.randomUUID().toString();
			request.setAttribute(Constants.RequestKey.REQUEST_ID, requestId);
		}
		org.apache.logging.log4j.ThreadContext.put(Constants.RequestKey.REQUEST_ID, (String) requestId);
		chain.doFilter(servletRequest, servletResponse);
		org.apache.logging.log4j.ThreadContext.clearAll();
	}

}
