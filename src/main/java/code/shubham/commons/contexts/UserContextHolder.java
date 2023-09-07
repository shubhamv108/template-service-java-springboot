package code.shubham.commons.contexts;

public class UserContextHolder {

	private static final ThreadLocal<Integer> CURRENT_USER = new ThreadLocal<>();

	public static void setUserId(Integer userId) {
		CURRENT_USER.set(userId);
	}

	public static Integer getUserId() {
		return CURRENT_USER.get();
	}

	public static void clear() {
		CURRENT_USER.remove();
	}

}
