package code.shubham.core.keygeneration.services;

import code.shubham.core.keygeneration.dao.repositories.AvailableKeyRepository;
import code.shubham.core.keygeneration.services.facades.KeyGenerationFacade;
import code.shubham.core.keygenerationcommons.service.IKeyGenerationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeyGenerationService implements IKeyGenerationService {

	private final KeyGenerationFacade facade;

	private final AvailableKeyRepository availableKeyRepository;

	@PostConstruct
	public void init() {
		this.scheduleKeyGenerationTask();
	}

	public String poll() {
		return this.facade.poll();
	}

	private void scheduleKeyGenerationTask() {
		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(() -> this.addKeys(100), 1l, 1l, TimeUnit.HOURS);
	}

	private void addKeys(int count) {
		if (this.availableKeyRepository.count() > 50)
			return;

		while (count-- > 0)
			this.facade.addRandomKey();
	}

}
