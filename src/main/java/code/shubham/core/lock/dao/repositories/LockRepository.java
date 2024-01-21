package code.shubham.core.lock.dao.repositories;

import code.shubham.core.lock.Constants;
import code.shubham.core.lock.dao.entites.Lock;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LockRepository extends JpaRepository<Lock, Long> {

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = Constants.Queries.INSERT)
	int insert(Long id, String name, int version, String owner, long timeToLiveInSeconds);

	Optional<Lock> findByName(String name);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = Constants.Queries.LOCK)
	int lock(String owner, long timeToLiveInSeconds, String name, int previousVersion);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = Constants.Queries.RENEW)
	int renew(long timeToLiveInSeconds, String name, String owner, int version);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = Constants.Queries.UNLOCK)
	int unlock(String name, String owner, int version);

}