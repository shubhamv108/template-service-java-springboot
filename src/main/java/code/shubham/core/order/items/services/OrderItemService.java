package code.shubham.core.order.items.services;

import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.utils.Utils;
import code.shubham.core.inventorycommons.IInventoryService;
import code.shubham.core.order.items.dao.entities.OrderItem;
import code.shubham.core.order.items.dao.entities.OrderItemStatus;
import code.shubham.core.order.items.dao.repositories.OrderItemRepository;
import code.shubham.core.ordermodels.OrderItemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemService {

	@Value("${order.item.status.sequence}")
	private List<OrderItemStatus> statusSequence;

	private final OrderItemRepository repository;

	// private final IInventoryService inventoryService;

	public List<OrderItem> save(final Long orderId, final List<OrderItemDTO> items) {
		final List<OrderItem> orderItems = items.stream()
			.map(orderItem -> OrderItem.builder()
				.orderId(orderId)
				.quantity(orderItem.getQuantity())
				.status(OrderItemStatus.CREATED)
				.inventoryId(orderItem.getInventoryId())
				.clientUniqueReferenceId(orderItem.getClientReferenceId())
				.build())
			.collect(Collectors.toList());

		final var persisted = this.save(orderItems, orderId);

		// items.forEach(item ->
		// this.inventoryService.applyQuantityOperation(item.getInventoryId(),
		// item.getQuantity(),
		// item.getClientReferenceId()));
		persisted.forEach(item -> item.setStatus(OrderItemStatus.AWAITING_PAYMENT));
		return this.save(persisted, orderId);
	}

	public OrderItem updateStatus(final OrderItemStatus completedStatus, final String uniqueReferenceId) {
		final OrderItem orderItem = this.repository.findByClientUniqueReferenceId(uniqueReferenceId)
			.orElseThrow(() -> new InvalidRequestException("uniqueReferenceId",
					"no product with uniqueReferenceId %s found for order", uniqueReferenceId));
		if (OrderItemStatus.DELIVERED.name().equals(orderItem.getStatus().name()))
			throw new InvalidRequestException("uniqueReferenceId", "order for product already completed");
		if (!completedStatus.name().equals(orderItem.getStatus().name()))
			throw new InvalidRequestException("completedStatus", "invalid completedStatus: %s", completedStatus.name());
		orderItem.setStatus(Utils.getNextInSequence(this.statusSequence, completedStatus));
		return this.repository.save(orderItem);
	}

	public List<OrderItem> fetchAllByOrderId(final Long orderId) {
		return this.repository.findAllByOrderId(orderId);
	}

	private List<OrderItem> save(final Iterable<OrderItem> items, final Long orderId) {
		log.info("[STARTED] persisting items for order id: {}", orderId);
		final var savedItems = this.repository.saveAll(items);
		log.info("[COMPLETED] persisted items for order id: {}", orderId);
		return savedItems;
	}

}
