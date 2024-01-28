package code.shubham.core.iam.dao.repositories;

import code.shubham.core.iam.dao.entities.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {

	List<AccountRole> findByAccountId(Long accountId);

}
