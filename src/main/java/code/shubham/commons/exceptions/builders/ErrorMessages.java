package code.shubham.commons.exceptions.builders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ErrorMessages {

	private String key;

	private Collection<String> values;

	private Map<String, Collection<String>> errorMessages;

	private final Collection<Map<String, Collection<String>>> errorMessagesList;

	private ErrorMessages() {
		this.key = null;
		this.values = new ArrayList<>();
		this.errorMessages = new HashMap<>();
		this.errorMessagesList = new ArrayList<>();
		this.errorMessagesList.add(this.errorMessages);
	}

	public static ErrorMessages builder() {
		return new ErrorMessages();
	}

	public ErrorMessages key(final String key) {
		this.errorMessages.remove(this.key);

		this.key = key;
		this.errorMessages.put(this.key, values);
		return this;
	}

	public ErrorMessages value(final String value, final String... placeholderValues) {
		this.values.add(String.format(value, placeholderValues));
		return this;
	}

	public ErrorMessages and() {
		this.key = null;
		this.values = new ArrayList<>();
		return this;
	}

	public ErrorMessages next() {
		this.errorMessages = new HashMap<>();
		this.errorMessagesList.add(this.errorMessages);
		return this;
	}

	public Collection<Map<String, Collection<String>>> build() {
		return this.errorMessagesList;
	}

	@Override
	public String toString() {
		return formulate(this.errorMessagesList);
	}

	public static String formulate(final Collection<Map<String, Collection<String>>> messages) {
		final StringBuilder builder = new StringBuilder("[\n");
		messages.stream().map(errorMessagesMap -> {
			StringBuilder mapBBuilder = new StringBuilder("{\n");
			errorMessagesMap.forEach((k, v) -> {
				mapBBuilder.append("\t").append('"').append(k).append('"').append(": ").append("[\n");
				v.forEach(e -> mapBBuilder.append("\t\t").append('"').append(e).append('"').append(",\n"));
				mapBBuilder.replace(mapBBuilder.lastIndexOf(","), mapBBuilder.lastIndexOf(",") + 1, "");
				mapBBuilder.append('\t').append("]\n");

				if ("]".equals(mapBBuilder.charAt(mapBBuilder.length() - 1)))
					mapBBuilder.append(',');

				mapBBuilder.append('\n');
			});
			mapBBuilder.append("},");
			return mapBBuilder;
		}).forEach(builder::append);
		builder.append("]\n");

		return builder.toString();
	}

}
