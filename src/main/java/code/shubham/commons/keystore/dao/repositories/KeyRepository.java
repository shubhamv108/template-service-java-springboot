package code.shubham.commons.keystore.dao.repositories;

import code.shubham.commons.keystore.dao.entities.KeyStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends JpaRepository<KeyStore, Long> {

	KeyStore findByPurpose(String purpose);

}