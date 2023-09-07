package code.shubham.commons.filters;

import code.shubham.commons.Constants;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@lombok.extern.slf4j.Slf4j
@org.springframework.stereotype.Component
@org.springframework.core.annotation.Order(3)
public class RequestResponseLoggingFilter implements Filter {
    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain chain) throws java.io.IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Long requestTimestamp = (Long) request.getAttribute(Constants.RequestKey.REQUEST_START_TIMESTAMP);
        java.util.Map<String, Object> requestHeaders = this.getHeaders(
                request.getHeaderNames().asIterator(),
                name -> request.getHeader(name));
//        Map<String, String[]> requestParameters = request.getParameterMap();

        String requestBody = ""; // servletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        log.info("Request:{Timestamp: {}, URI: {} {}, QueryParameters: {}, Headers: {}, Body: {}}",
                requestTimestamp, request.getMethod(), request.getRequestURI(),
                request.getQueryString(), requestHeaders, requestBody);
        org.springframework.web.util.ContentCachingResponseWrapper responseCacheWrapperObject = new org.springframework.web.util.ContentCachingResponseWrapper(response);

        chain.doFilter(servletRequest, servletResponse);

        java.util.Map<String, Object> responseHeaders = this.getHeaders(
                request.getHeaderNames().asIterator(),
                name -> response.getHeader(name));
        byte[] responseArray = responseCacheWrapperObject.getContentAsByteArray();
        String responseBody = new String(responseArray, responseCacheWrapperObject.getCharacterEncoding());
        responseCacheWrapperObject.copyBodyToResponse();

        Long requestStartTimestamp = (Long) request.getAttribute(Constants.RequestKey.REQUEST_START_TIMESTAMP);
        log.info("Response:{StatusCode: {}, Headers: {}, Body: {} StartTime: {}, EndTime: {}}",
                response.getStatus(),
                responseHeaders,
                responseBody,
                requestStartTimestamp,
                System.currentTimeMillis());
    }

    private java.util.Map<String, Object> getHeaders(final java.util.Iterator<String> headerNames,
                                                     final java.util.function.Function<String, Object> headersValue) {
        java.util.Map<String, Object> headers = new java.util.HashMap<>();
        if (headerNames != null) {
            String headerName;
            while (headerNames.hasNext()) {
                headerName = headerNames.next();
                headers.put(headerName, headersValue.apply(headerName));
            }
        }
        return headers;
    }
}
