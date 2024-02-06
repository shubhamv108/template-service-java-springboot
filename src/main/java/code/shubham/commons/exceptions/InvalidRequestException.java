package code.shubham.commons.exceptions;

import code.shubham.commons.exceptions.builders.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends ClientException {

	private final Collection<Map<String, Collection<String>>> errorMessagesList = new ArrayList<>();

	protected Map<String, Collection<String>> errorMessages;

	public InvalidRequestException() {
		this(null);
	}

	public InvalidRequestException(String key, String message, String... args) {
		this(null);
		this.errorMessages.put(key, new ArrayList<>(Arrays.asList(String.format(message, args))));
	}

	public InvalidRequestException(final Map<String, Collection<String>> errorMessages) {
		this.errorMessages = new LinkedHashMap<>();
		if (errorMessages != null)
			this.errorMessages.putAll(errorMessages);
	}

	public void next() {
		if (isCurrentEmpty())
			return;
		this.errorMessagesList.add(this.errorMessages);
		this.errorMessages = new LinkedHashMap<>();
	}

	public InvalidRequestException put(final String errorKey, final Collection<String> errorMessages) {
		this.errorMessages.put(errorKey, errorMessages);
		return this;
	}

	@SuppressWarnings("unchecked")
	public InvalidRequestException putErrorMessage(final String errorKey, final String errorMessage, String... params) {
		return putErrorMessage(errorKey, String.format(errorMessage, params));
	}

	public InvalidRequestException putErrorMessage(final String errorKey, final String errorMessage) {
		Collection<String> errorMessages = this.errorMessages.get(errorKey);
		if (java.util.Objects.isNull(errorMessages)) {
			errorMessages = new ArrayList<>();
			this.errorMessages.put(errorKey, errorMessages);
		}
		errorMessages.add(errorMessage);
		return get();
	}

	public InvalidRequestException putErrorMessages(final Map<String, Collection<String>> errorMessages) {
		if (this.errorMessages == null) {
			this.errorMessages = errorMessages;
		}
		else {
			errorMessages.forEach((k, v) -> v.forEach(errorMessage -> putErrorMessage(k, errorMessage)));
		}
		return get();
	}

	private InvalidRequestException get() {
		return this;
	}

	private Map<String, Collection<String>> getErrorMessages() {
		return errorMessages;
	}

	@Override
	public String toString() {
		return ErrorMessages.formulate(this.errorMessagesList);
	}

	public void tryThrow() {
		if (!isEmpty())
			throw this;
	}

	public boolean isEmpty() {
		return this.errorMessagesList.isEmpty() && isCurrentEmpty();
	}

	public boolean isCurrentEmpty() {
		return this.errorMessages.isEmpty();
	}

	public Collection<Map<String, Collection<String>>> getOriginalErrors() {
		if (this.errorMessagesList.isEmpty())
			this.errorMessagesList.add(errorMessages);
		return this.errorMessagesList;
	}

}