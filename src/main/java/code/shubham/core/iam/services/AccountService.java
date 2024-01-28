package code.shubham.core.iam.services;

import code.shubham.core.iam.dao.entities.Account;
import code.shubham.core.iam.dao.repositories.AccountRepository;
import code.shubham.core.iamcommons.IAccountService;
import code.shubham.core.iammodels.GetOrCreateAccount;
import code.shubham.core.iammodels.GetAccountResponse;
import code.shubham.core.iammodels.AccountDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

	private final AccountRepository repository;

	private final AccountRoleService userRoleService;

	@Override
	public GetAccountResponse getOrCreate(final GetOrCreateAccount.Request request) {
		final Optional<Account> existing = this.repository.findByEmail(request.getEmail());

		Account persisted = null;
		if (existing.isPresent())
			persisted = existing.get();
		else
			persisted = this.create(Account.builder().name(request.getName()).email(request.getEmail()).build());
		return GetAccountResponse.builder()
			.account(new AccountDTO(persisted.getId(), persisted.getEmail()))
			.roles(this.userRoleService.getAllRoles(persisted.getId()))
			.build();
	}

	public Account create(final Account account) {
		final Account persisted = this.repository.save(account);
		this.userRoleService.setRoleToAccount("USER", account.getId());
		return persisted;
	}

	@Override
	public GetAccountResponse getById(final Long accountId) {
		return this.repository.findById(accountId)
			.map(account -> GetAccountResponse.builder()
				.account(new AccountDTO(accountId, account.getEmail()))
				.roles(this.userRoleService.getAllRoles(accountId))
				.build())
			.orElse(null);
	}

}
