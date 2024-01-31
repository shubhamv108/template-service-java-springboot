package code.shubham.commons.utils;

import code.shubham.commons.models.ServiceResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ResponseUtils {

	public static ResponseEntity<?> getOK() {
		return ResponseEntity.status(org.springframework.http.HttpStatus.OK).build();
	}

	public static ResponseEntity<?> getErrorResponseEntity(int statusCode, Object errors) {
		return getResponseEntity(statusCode, null, errors);
	}

	public static ResponseEntity<?> getResponseEntity(org.springframework.http.HttpStatus status) {
		return getResponseEntity(status.value(), null, null);
	}

	public static ResponseEntity<?> getResponseEntity(int statusCode) {
		return getResponseEntity(statusCode, null, null);
	}

	public static ResponseEntity<?> getDataResponseEntity(org.springframework.http.HttpStatus status, Object data) {
		return getResponseEntity(status.value(), data, null);
	}

	public static ResponseEntity<?> getDataResponseEntity(int statusCode, Object data) {
		return getResponseEntity(statusCode, data, null);
	}

	public static ResponseEntity<?> getResponseEntity(int statusCode, Object data, Object errors) {
		ServiceResponse response = ServiceResponse.builder().statusCode(statusCode).data(data).error(errors).build();
		return ResponseEntity.status(statusCode).body(response);
	}

	public static ResponseEntity<?> getResponseEntity(int statusCode, Object data, Object errors, String headerName,
			String... headerValues) {
		ServiceResponse response = ServiceResponse.builder().statusCode(statusCode).data(data).error(errors).build();
		return ResponseEntity.status(statusCode).header(headerName, headerValues).body(response);
	}

	public static ResponseEntity<?> getResponseEntity(int statusCode, Object data,
			Map<String, java.util.List<String>> headers) {
		return getResponseEntity(statusCode, data, null, headers);
	}

	public static ResponseEntity<?> getResponseEntity(int statusCode, Object data,
			Map<String, java.util.List<String>> headers, String headerKey, String... headerValues) {
		headers.put(headerKey, Arrays.asList(headerValues));
		return getResponseEntity(statusCode, data, null, headers);
	}

	public static ResponseEntity<?> getResponseEntity(final int statusCode, final Object data, final Object errors,
			final Map<String, java.util.List<String>> headers) {
		final ServiceResponse response = ServiceResponse.builder()
			.statusCode(statusCode)
			.data(data)
			.error(errors)
			.build();
		HttpHeaders httpHeaders = null;
		if (headers != null) {
			httpHeaders = new HttpHeaders();
			headers.forEach(httpHeaders::addAll);
		}
		return ResponseEntity.status(statusCode).headers(httpHeaders).body(response);
	}

	public static ResponseEntity<?> redirect(final String redirectURI) throws URISyntaxException {
		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(new URI(redirectURI));
		return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).headers(headers).build();
	}

}
