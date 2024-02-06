package code.shubham.commons.exceptions;

import code.shubham.commons.exceptions.builders.ErrorMessages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	private ErrorMessages errorMessages;

	public NotFoundException(final String key, final String value, final String... placeholderValues) {
		this.errorMessages = ErrorMessages.builder().key(key).value(value, placeholderValues);
	}

	@Override
	public String getMessage() {
		return Optional.ofNullable(this.errorMessages).map(ErrorMessages::toString).orElse("");
	}

}
