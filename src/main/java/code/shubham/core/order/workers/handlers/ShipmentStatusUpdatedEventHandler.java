package code.shubham.core.order.workers.handlers;

import code.shubham.commons.handlers.EventHandler;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.core.order.dao.entities.OrderStatus;
import code.shubham.core.order.items.dao.entities.OrderItem;
import code.shubham.core.order.items.dao.entities.OrderItemStatus;
import code.shubham.core.order.items.services.OrderItemService;
import code.shubham.core.order.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
// @Scope("prototype")
// @Component("ShipmentStatusUpdatedEventHandler")
@RequiredArgsConstructor
public class ShipmentStatusUpdatedEventHandler implements EventHandler {

	private final OrderService service;

	private final OrderItemService orderItemService;

	@Override
	public Object handle(final Event event) {
		log.debug(String.format("Event Data: %s", event.getData()));
		final Map<String, Object> data = JsonUtils.as(event.getData(), HashMap.class);
		final OrderItem orderItem = this.getCompletedOrderProductStatusFromShipmentStatus((String) data.get("status"))
			.map(orderItemStatus -> this.orderItemService.updateStatus(orderItemStatus, event.getUniqueReferenceId()))
			.orElseGet(() -> {
				log.info("[SKIP] Event skipped");
				return null;
			});

		if (OrderItemStatus.COMPLETED.name().equals(orderItem.getStatus().name()))
			return this.service.updateStatus(Long.valueOf((Long) data.get("orderId")), OrderStatus.CREATED);

		return orderItem;
	}

	private Optional<OrderItemStatus> getCompletedOrderProductStatusFromShipmentStatus(final String shipmentStatus) {
		switch (shipmentStatus) {
			case "PREPARE_TO_DISPATCH":
				return Optional.of(OrderItemStatus.CREATED);
			case "DELIVERED":
				return Optional.of(OrderItemStatus.SHIPPED);
		}
		return Optional.empty();
	}

}
