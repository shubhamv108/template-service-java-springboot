package code.shubham.core.order.items.dao.repositories;

import code.shubham.core.order.items.dao.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	List<OrderItem> findAllByOrderId(Long orderId);

	Optional<OrderItem> findByClientUniqueReferenceId(String uniqueReferenceId);

}
