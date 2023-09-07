package code.shubham.commons.exceptions;

import code.shubham.commons.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<?> handleAccessDeniedException(Exception ex, WebRequest request) {
		return ResponseUtils.getErrorResponseEntity(HttpStatus.FORBIDDEN.value(), "Access denied message here");
	}

	@ExceptionHandler({ SQLIntegrityConstraintViolationException.class })
	public ResponseEntity<?> handleSQLIntegrityConstraintViolationException(
			SQLIntegrityConstraintViolationException exception, WebRequest request) {
		return ResponseUtils.getErrorResponseEntity(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
	}

	@ExceptionHandler({ AuthorizationClientException.class })
	public ResponseEntity<?> handleAuthorizationClientException(AuthorizationClientException exception,
			WebRequest request) {
		return ResponseUtils.getErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
	}

	@ExceptionHandler({ InvalidRequestException.class })
	public ResponseEntity<?> handleInvalidRequestException(InvalidRequestException exception, WebRequest request) {
		return ResponseUtils.getErrorResponseEntity(HttpStatus.BAD_REQUEST.value(), exception.getOriginalErrors());
	}

	@ExceptionHandler({ BlobStoreException.class })
	public ResponseEntity<?> handleAWSS3BlobStoreException(InvalidRequestException exception, WebRequest request) {
		return ResponseUtils.getErrorResponseEntity(HttpStatus.BAD_REQUEST.value(), exception.getOriginalErrors());
	}

	@ExceptionHandler({ ServiceInvocationClientException.class })
	public ResponseEntity<?> handleServiceInvocationClientException(InvalidRequestException exception,
			WebRequest request) {
		return ResponseUtils.getErrorResponseEntity(HttpStatus.SERVICE_UNAVAILABLE.value(),
				exception.getOriginalErrors());
	}

}