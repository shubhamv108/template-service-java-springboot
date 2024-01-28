package code.shubham.commons.contexts;

public class AccountIDContextHolder {

	private static final ThreadLocal<Long> CONTEXT = new ThreadLocal<>();

	public static void set(final Long id) {
		CONTEXT.set(id);
	}

	public static Long get() {
		return CONTEXT.get();
	}

	public static void clear() {
		CONTEXT.remove();
	}

}
