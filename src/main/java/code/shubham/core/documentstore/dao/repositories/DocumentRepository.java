package code.shubham.core.documentstore.dao.repositories;

import code.shubham.core.documentstore.dao.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {

	Optional<Document> findByOwnerAndName(Long owner, String name);

}
