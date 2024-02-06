package code.shubham.core.keygeneration.dao.repositories;

import code.shubham.core.keygeneration.dao.entities.UsedKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedKeyRepository extends JpaRepository<UsedKey, String> {

}
