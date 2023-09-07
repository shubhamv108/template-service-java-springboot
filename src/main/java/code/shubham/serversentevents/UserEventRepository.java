package code.shubham.serversentevents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;

@Repository
public interface UserEventRepository extends JpaRepository<UserEvent, Long> {

	Collection<UserEvent> findByUserId(String userId);

}
