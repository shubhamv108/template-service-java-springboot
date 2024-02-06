package code.shubham.core.keygeneration.services.facades;

import code.shubham.commons.utils.StringUtils;
import code.shubham.core.keygeneration.dao.entities.AvailableKey;
import code.shubham.core.keygeneration.dao.entities.UsedKey;
import code.shubham.core.keygeneration.dao.repositories.AvailableKeyRepository;
import code.shubham.core.keygeneration.dao.repositories.UsedKeyRepository;
import code.shubham.core.keygenerationcommons.strategies.IKeyGenerateStrategy;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeyGenerationFacade {

	private final AvailableKeyRepository availableKeyRepository;

	private final UsedKeyRepository usedKeyRepository;

	@Autowired
	@Qualifier("RandomCharacterKeyGenerateStrategy")
	private IKeyGenerateStrategy randomCharacterKeyGenerateStrategy;

	@Transactional
	public String poll() {
		final String name = this.getAvailable()
			.filter(StringUtils::isNotEmpty)
			.orElse(this.randomCharacterKeyGenerateStrategy.generate(null, 7));
		this.usedKeyRepository.save(UsedKey.builder().name(name).build());
		this.availableKeyRepository.deleteById(name);
		return name;
	}

	public boolean addRandomKey() {
		boolean result = false;
		int retry = 0;
		while (!result && retry++ < 3) {
			final String name = this.randomCharacterKeyGenerateStrategy.generate(null, 7);
			result = this.addKey(name);
		}
		return result;
	}

	private boolean addKey(final String name) {
		final Optional<UsedKey> usedKey = this.usedKeyRepository.findById(name);
		if (usedKey.isEmpty()) {
			this.availableKeyRepository.save(AvailableKey.builder().name(name).build());
			return true;
		}
		return false;
	}

	private Optional<String> getAvailable() {
		// return this.availableKeyRepository.findAny();
		return Optional.empty();
	}

}
