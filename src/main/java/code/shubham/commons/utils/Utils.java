package code.shubham.commons.utils;

import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.contexts.AccountIDContextHolder;
import code.shubham.commons.exceptions.InvalidRequestException;

import java.util.Iterator;
import java.util.List;

public class Utils {

	public static <T> T getNextInSequence(final List<T> sequence, final T current) {
		final Iterator<T> iterator = sequence.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().equals(current)) {
				if (iterator.hasNext())
					return iterator.next();
				break;
			}
		}
		return null;
	}

	public static void validateAccountOrThrowException(final Long accountId) {
		if (!accountId.equals(AccountIDContextHolder.get()) && !RoleContextHolder.isAdmin())
			throw new InvalidRequestException("accountId",
					"Account with accountId: %s not allowed to perform the operation", String.valueOf(accountId));
	}

}
