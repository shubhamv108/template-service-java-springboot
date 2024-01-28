package code.shubham.core.order.workers.handlers;

import code.shubham.commons.handlers.EventHandler;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.core.ordermodels.CreateOrderCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import code.shubham.core.order.services.OrderService;

@Slf4j
@Scope("prototype")
@Component("CreateOrderCommandEventHandler")
@RequiredArgsConstructor
public class CreateOrderCommandEventHandler implements EventHandler {

	private final OrderService service;

	@Override
	public Object handle(final Event event) {
		final CreateOrderCommand command = JsonUtils.as(event.getData(), CreateOrderCommand.class);
		return this.service.create(command);
	}

}
