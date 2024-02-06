package code.shubham.core.keygeneration.dao.repositories;

import code.shubham.core.keygeneration.dao.entities.AvailableKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvailableKeyRepository extends JpaRepository<AvailableKey, String> {

	@Procedure("POLL_ANY_AVAILABLE_KEY")
	Optional<String> findAny();

}
