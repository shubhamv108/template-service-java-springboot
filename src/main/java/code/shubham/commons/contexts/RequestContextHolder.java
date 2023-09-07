package code.shubham.commons.contexts;

public class RequestContextHolder {

	private static final ThreadLocal<java.util.Map<String, Object>> requestContext = getNew();

	private static ThreadLocal<java.util.Map<String, Object>> getNew() {
		ThreadLocal<java.util.Map<String, Object>> requestContext = new ThreadLocal<>();
		requestContext.set(new java.util.LinkedHashMap<>());
		return requestContext;
	}

	public static void set(String key, Object value) {
		requestContext.get().put(key, value);
	}

	public static Object get(String key) {
		return requestContext.get().get(key);
	}

	public static void clear() {
		requestContext.remove();
	}

}
