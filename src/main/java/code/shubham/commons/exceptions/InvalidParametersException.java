package code.shubham.commons.exceptions;

public class InvalidParametersException extends InvalidRequestException {

    public InvalidParametersException(final java.util.Map<String, java.util.Collection<String>> messages) {
        super(messages);
    }

    @Override
    public String getMessage() {
        StringBuilder errorMessageBuilder = new StringBuilder();
        if (this.errorMessages != null)
            for (java.util.Map.Entry<String, java.util.Collection<String>> entry : this.errorMessages.entrySet()) {
                String errorMessage = entry.getKey();
                java.util.Collection<String> params = entry.getValue();
                errorMessageBuilder
                        .append(String.format(String.join(", ", params), errorMessage));
            }
        return errorMessageBuilder.toString();
    }

}
