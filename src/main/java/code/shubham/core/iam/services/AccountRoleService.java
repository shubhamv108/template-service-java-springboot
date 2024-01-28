package code.shubham.core.iam.services;

import code.shubham.commons.exceptions.InvalidParameterException;
import code.shubham.core.iam.dao.entities.AccountRole;
import code.shubham.core.iam.dao.repositories.AccountRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountRoleService {

	private final AccountRoleRepository repository;

	private final RoleService roleService;

	public AccountRole setRoleToAccount(final String roleName, final Long accountId) {
		return this.roleService.getRoleByName(roleName)
			.map(role -> this.repository.save(AccountRole.builder().role(role).accountId(accountId).build()))
			.orElseThrow(() -> new InvalidParameterException("No role with name: %s", roleName));
	}

	public Collection<String> getAllRoles(final Long userId) {
		return this.repository.findByAccountId(userId)
			.stream()
			.map(accountRole -> accountRole.getRole().getName())
			.collect(Collectors.toSet());
	}

}
