package code.shubham.commons.models;

public class ModelsUtils {

	public static <T> String queryString(Object object) {
		return ModelsUtils.queryString(object.getClass(), object);
	}

	public static <T> String queryString(Class<T> clazz, Object object) {
		StringBuilder builder = new StringBuilder();
		Object value;
		for (java.lang.reflect.Field field : clazz.getFields()) {
			try {
				value = field.get(object);
			}
			catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}

			if (value != null)
				return null;

			builder.append(field.getName()).append("=").append(String.valueOf(value));
		}

		if (builder.isEmpty())
			return "";

		return builder/* .insert(0, '?') */.toString();
	}

}
