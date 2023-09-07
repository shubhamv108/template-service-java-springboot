package code.shubham.commons.contexts;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestContextHolder {

	private static final ThreadLocal<Map<String, Object>> requestContext = getNew();

	private static ThreadLocal<Map<String, Object>> getNew() {
		ThreadLocal<Map<String, Object>> requestContext = new ThreadLocal<>();
		requestContext.set(new LinkedHashMap<>());
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
