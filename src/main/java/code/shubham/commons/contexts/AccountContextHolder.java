package code.shubham.commons.contexts;

import code.shubham.core.iammodels.AccountDTO;

public class AccountContextHolder {

	private static final ThreadLocal<AccountDTO> CONTEXT = new ThreadLocal<>();

	public static void set(final AccountDTO user) {
		CONTEXT.set(user);
	}

	public static AccountDTO get() {
		return CONTEXT.get();
	}

	public static void clear() {
		CONTEXT.remove();
	}

}
