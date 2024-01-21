package code.shubham.commons.dao;

import code.shubham.commons.contexts.UserIDContextHolder;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class UserIdAuditorAware implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {
		final Optional<Long> userId = Optional.ofNullable(UserIDContextHolder.get());
		if (userId.isPresent())
			return userId;

		return Optional.ofNullable(-1L);
	}

}