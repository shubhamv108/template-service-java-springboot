package code.shubham.commons.exceptions;

public class InternalServerError extends RuntimeException {

	private java.util.Map<String, java.util.List<String>> message;

	public InternalServerError() {
	}

	public InternalServerError(String message) {
		super(message);
	}

	public InternalServerError(java.util.Map<String, java.util.List<String>> message) {
		this.message = message;
	}

}
