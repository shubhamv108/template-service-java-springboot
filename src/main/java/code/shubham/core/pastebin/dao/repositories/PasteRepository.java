package code.shubham.core.pastebin.dao.repositories;

import code.shubham.core.pastebin.dao.entities.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasteRepository extends JpaRepository<Paste, Long> {

	Optional<Paste> findByKeyName(String key);

	Optional<Paste> findByKeyNameAndAccountId(String key, Long accountId);

}
