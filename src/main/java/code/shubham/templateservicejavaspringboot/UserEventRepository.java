package code.shubham.templateservicejavaspringboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserEventRepository extends JpaRepository<UserEvent, Long> {

	Collection<UserEvent> findByUserId(String userId);

}
