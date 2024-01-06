package code.shubham.commons.validators;

public interface IValidator<OBJECT> {

	String IS_EMPTY = "%s is empty.";

	String MUST_NOT_BE_EMPTY = "%s must not be empty.";

	IValidator<OBJECT> validate(OBJECT object);

	IValidator<OBJECT> validateOrThrowException(OBJECT object);

	boolean hasMessages();

	java.util.Map<String, java.util.Collection<String>> getResult();

}