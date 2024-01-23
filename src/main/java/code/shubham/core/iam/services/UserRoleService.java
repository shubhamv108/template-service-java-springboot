package code.shubham.core.iam.services;

import code.shubham.commons.exceptions.InvalidParameterException;
import code.shubham.core.iam.dao.entities.UserRole;
import code.shubham.core.iam.dao.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRoleService {

	private final UserRoleRepository repository;

	private final RoleService roleService;

	public UserRole setRoleToUser(final String roleName, final Long userId) {
		return this.roleService.getRoleByName(roleName)
			.map(role -> this.repository.save(UserRole.builder().role(role).userId(userId).build()))
			.orElseThrow(() -> new InvalidParameterException("No role with name: %s", roleName));
	}

	public Collection<String> getAllRoles(final Long userId) {
		return this.repository.findByUserId(userId)
			.stream()
			.map(userRole -> userRole.getRole().getName())
			.collect(Collectors.toSet());
	}

}
