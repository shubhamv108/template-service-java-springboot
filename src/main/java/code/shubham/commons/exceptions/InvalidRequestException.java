package code.shubham.commons.exceptions;

public class InvalidRequestException extends ClientException {

    private final java.util.Collection<java.util.Map<String, java.util.Collection<String>>> errorMessagesList = new java.util.ArrayList<>();
    protected java.util.Map<String, java.util.Collection<String>> errorMessages;

    public InvalidRequestException() {
        this(null);
    }

    public InvalidRequestException(String key, String message) {
        this(null);
        this.errorMessages.put(key, new java.util.ArrayList<>(java.util.Arrays.asList(message)));
    }
    public InvalidRequestException(final java.util.Map<String, java.util.Collection<String>> errorMessages) {
        this.errorMessages = new java.util.LinkedHashMap<>();
        if (errorMessages != null)
            this.errorMessages.putAll(errorMessages);
    }

    public void next() {
        if (isCurrentEmpty())
            return;
        this.errorMessagesList.add(this.errorMessages);
        this.errorMessages = new java.util.LinkedHashMap<>();
    }
    public code.shubham.commons.exceptions.InvalidRequestException put(final String errorKey, final java.util.Collection<String> errorMessages) {
        this.errorMessages.put(errorKey, errorMessages);
        return this;
    }

    @SuppressWarnings("unchecked")
    public code.shubham.commons.exceptions.InvalidRequestException putErrorMessage(final String errorKey, final String errorMessage, String... params) {
        return putErrorMessage(errorKey, String.format(errorMessage, params));
    }

    public code.shubham.commons.exceptions.InvalidRequestException putErrorMessage(final String errorKey, final String errorMessage) {
        java.util.Collection<String> errorMessages = this.errorMessages.get(errorKey);
        if (java.util.Objects.isNull(errorMessages)) {
            errorMessages = new java.util.ArrayList<>();
            this.errorMessages.put(errorKey, errorMessages);
        }
        errorMessages.add(errorMessage);
        return get();
    }

    public code.shubham.commons.exceptions.InvalidRequestException putErrorMessages(final java.util.Map<String, java.util.Collection<String>> errorMessages) {
        if (this.errorMessages == null) {
            this.errorMessages = errorMessages;
        } else {
            errorMessages.forEach((k, v) -> v.forEach(errorMessage -> putErrorMessage(k, errorMessage)));
        }
        return get();
    }

    private code.shubham.commons.exceptions.InvalidRequestException get() {
        return this;
    }

    private java.util.Map<String, java.util.Collection<String>> getErrorMessages() {
        return errorMessages;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[\n");
        this.errorMessagesList.stream().map(errorMessagesMap -> {
            StringBuilder mapBBuilder = new StringBuilder("{\n");
            errorMessagesMap.forEach((k, v) -> {
                mapBBuilder.append("\t").append('"').append(k).append('"').append(": ").append("[\n");
                v.forEach(e -> mapBBuilder.append("\t\t").append('"').append(e).append('"').append(",\n"));
                mapBBuilder.replace(mapBBuilder.lastIndexOf(","), mapBBuilder.lastIndexOf(",") + 1, "");
                mapBBuilder.append('\t').append("]\n");
                if ("]".equals(mapBBuilder.charAt(mapBBuilder.length() - 1))) {
                    mapBBuilder.append(',');
                }
                mapBBuilder.append('\n');
            });
            mapBBuilder.append("},");
            return mapBBuilder;
        }).forEach(builder::append);
        builder.append("]\n");
        return builder.toString();
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

    public java.util.Collection<java.util.Map<String, java.util.Collection<String>>> getOriginalErrors() {
        return this.errorMessagesList;
    }
}