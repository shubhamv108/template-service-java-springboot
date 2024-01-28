package code.shubham.core.accountprofiles.dao.repositories;

import code.shubham.core.accountprofiles.dao.entities.AccountProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountProfileRepository extends JpaRepository<AccountProfile, Long> {

	Optional<AccountProfile> findByAccountId(Long accountId);

}
