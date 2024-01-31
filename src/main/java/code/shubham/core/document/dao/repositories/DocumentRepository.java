package code.shubham.core.document.dao.repositories;

import code.shubham.core.document.dao.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

	Optional<Document> findByOwnerAndName(String owner, String name);

}
