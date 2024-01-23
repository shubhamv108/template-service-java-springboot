package code.shubham.core.iam.services;

import code.shubham.core.iam.dao.entities.Role;
import code.shubham.core.iam.dao.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

	private final RoleRepository repository;

	private final Map<String, Role> cache = new HashMap<>();

	@PostConstruct
	public void init() {
		this.repository.findAnyTen().stream().forEach(role -> this.cache.put(role.getName(), role));
	}

	public Optional<Role> getRoleByName(final String name) {
		return Optional.ofNullable(this.cache.getOrDefault(name, this.repository.findByName(name).orElse(null)));
	}

}
