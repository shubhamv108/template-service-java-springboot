package code.shubham.core.iam.dao.repositories;

import code.shubham.core.iam.dao.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query(value = "select * from accounts where email = ?", nativeQuery = true)
	Optional<Account> findByEmail(String email);

}
