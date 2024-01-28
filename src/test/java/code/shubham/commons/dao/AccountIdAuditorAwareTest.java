package code.shubham.commons.dao;

import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.contexts.AccountIDContextHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class AccountIdAuditorAwareTest {

	private IdAuditorAware auditorAware;

	@BeforeEach
	void setUp() {
		this.auditorAware = new IdAuditorAware();
	}

	@Test
	void getCurrentAuditor_Success() {
		AccountIDContextHolder.set(TestCommonConstants.ACCOUNT_ID);
		final IdAuditorAware auditorAware = new IdAuditorAware();

		final Optional<Long> response = auditorAware.getCurrentAuditor();

		Assertions.assertTrue(response.isPresent());
		Assertions.assertEquals(TestCommonConstants.ACCOUNT_ID, response.get());
	}

	@Test
	void getCurrentAuditor_without_userId_in_context() {
		AccountIDContextHolder.set(null);
		final IdAuditorAware auditorAware = new IdAuditorAware();

		final Optional<Long> response = auditorAware.getCurrentAuditor();

		Assertions.assertTrue(response.isPresent());
		Assertions.assertEquals(-1, response.get());
	}

}