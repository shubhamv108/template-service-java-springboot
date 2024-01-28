package code.shubham.commons.dao;

import code.shubham.commons.contexts.AccountIDContextHolder;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class IdAuditorAware implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {
		final Optional<Long> auditorId = Optional.ofNullable(AccountIDContextHolder.get());
		if (auditorId.isPresent())
			return auditorId;

		return Optional.ofNullable(-1L);
	}

}