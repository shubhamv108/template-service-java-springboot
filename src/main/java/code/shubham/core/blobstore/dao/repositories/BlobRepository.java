package code.shubham.core.blobstore.dao.repositories;

import code.shubham.core.blobstore.dao.entities.Blob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlobRepository extends JpaRepository<Blob, Long> {

	Optional<Blob> findByIdAndOwner(Long id, String owner);

}
