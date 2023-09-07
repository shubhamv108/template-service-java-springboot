package code.shubham.commons.contexts;

public class TenantContextHolder {

	private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

	public static void setTenant(String userId) {
		CURRENT_TENANT.set(userId);
	}

	public static String getTenant() {
		return CURRENT_TENANT.get();
	}

	public static void clear() {
		CURRENT_TENANT.remove();
	}

}
