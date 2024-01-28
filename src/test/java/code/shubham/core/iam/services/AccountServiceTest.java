package code.shubham.core.iam.services;

import code.shubham.commons.TestCommonConstants;
import code.shubham.core.iam.dao.repositories.AccountRepository;
import code.shubham.core.iam.dao.repositories.AccountRoleRepository;
import code.shubham.core.iammodels.GetOrCreateAccount;
import code.shubham.core.iammodels.GetAccountResponse;
import code.shubham.test.AbstractSpringBootTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AccountServiceTest extends AbstractSpringBootTest {

	@Autowired
	private AccountService service;

	@Autowired
	private AccountRepository repository;

	@Autowired
	private AccountRoleRepository accountRoleRepository;

	@Test
	void getOrCreate() {
		final GetAccountResponse response = this.service.getOrCreate(
				GetOrCreateAccount.Request.builder().name("Shubham").email(TestCommonConstants.USER_EMAIL).build());

		final var user = this.repository.findByEmail(TestCommonConstants.USER_EMAIL);
		final var roles = this.accountRoleRepository.findByAccountId(user.get().getId());
		Assertions.assertEquals(response.getAccount().email(), TestCommonConstants.USER_EMAIL);
		Assertions.assertNotNull(response.getAccount().id());
		Assertions.assertEquals(response.getRoles().stream().findFirst().get(), "USER");
		Assertions.assertTrue(user.isPresent());
		Assertions.assertEquals("Shubham", user.get().getName());
		Assertions.assertEquals(1, roles.size());
		Assertions.assertEquals("USER", roles.get(0).getRole().getName());
	}

	@Test
	void getById() {
	}

}