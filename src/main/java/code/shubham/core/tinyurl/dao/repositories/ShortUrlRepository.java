package code.shubham.core.tinyurl.dao.repositories;

import code.shubham.core.tinyurl.dao.entities.ShortURL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortURL, Long> {

	@Query(nativeQuery = true,
			value = "SELECT url FROM short_urls WHERE key_name = ? AND (account_id IS NULL OR account_id = ?)")
	Optional<String> findURL(String shortUrl, Long accountId);

}
