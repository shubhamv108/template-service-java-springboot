package code.shubham.commons.dao;

import code.shubham.commons.TestCommonConstants;
import code.shubham.commons.contexts.UserIDContextHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class UserIdAuditorAwareTest {

	private UserIdAuditorAware auditorAware;

	@BeforeEach
	void setUp() {
		this.auditorAware = new UserIdAuditorAware();
	}

	@Test
	void getCurrentAuditor_Success() {
		UserIDContextHolder.set(TestCommonConstants.USER_ID);
		final UserIdAuditorAware auditorAware = new UserIdAuditorAware();

		final Optional<Long> response = auditorAware.getCurrentAuditor();

		Assertions.assertTrue(response.isPresent());
		Assertions.assertEquals(TestCommonConstants.USER_ID, response.get());
	}

	@Test
	void getCurrentAuditor_without_userId_in_context() {
		UserIDContextHolder.set(null);
		final UserIdAuditorAware auditorAware = new UserIdAuditorAware();

		final Optional<Long> response = auditorAware.getCurrentAuditor();

		Assertions.assertTrue(response.isPresent());
		Assertions.assertEquals(-1, response.get());
	}

}