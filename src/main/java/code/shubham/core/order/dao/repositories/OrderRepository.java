package code.shubham.core.order.dao.repositories;

import code.shubham.core.order.dao.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findAllByAccountId(Long accountId);

	Optional<Order> findById(Long id);

	Optional<Order> findByIdAndAccountId(Long orderId, Long accountId);

}
