package code.shubham.core.lock.services;

import code.shubham.commons.generators.id.implementations.SnowflakeSequenceIdGenerator;
import code.shubham.core.lock.dao.entites.Lock;
import code.shubham.core.lock.dao.repositories.LockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LockService {

	private final LockRepository repository;

	@Autowired
	public LockService(final LockRepository repository) {
		this.repository = repository;
	}

	public boolean lock(final String name, final int previousVersion, final String owner,
			final long timeToLiveInSeconds) {
		if (previousVersion == 0)
			return this.repository.insert(SnowflakeSequenceIdGenerator.getInstance().generate(), name, 1, owner,
					timeToLiveInSeconds) == 1;
		else
			return this.repository.lock(owner, timeToLiveInSeconds, name, previousVersion) == 1;
	}

	public boolean renew(final String name, final int version, final String owner, final long timeToLiveInSeconds) {
		return this.repository.renew(timeToLiveInSeconds, name, owner, version) == 1;
	}

	public boolean unlock(final String name, final String owner, final int version) {
		return this.repository.unlock(name, owner, version) == 1;
	}

	public Optional<Lock> fetchByName(final String name) {
		return this.repository.findByName(name);
	}

}
