package code.shubham.core.order.orchestrator;

import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.utils.UUIDUtils;
import code.shubham.core.cartcommons.ICartService;
import code.shubham.core.cartmodels.CartItemDTO;
import code.shubham.core.order.services.OrderService;
import code.shubham.core.ordermodels.CreateOrderCommand;
import code.shubham.core.ordermodels.CreateOrderRequest;
import code.shubham.core.ordermodels.OrderDataDTO;
import code.shubham.core.ordermodels.OrderItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Scope("prototype")
@Component("CreateOrderOrchestrator")
@RequiredArgsConstructor
public class CreateOrderOrchestrator {

	private final OrderService service;

	private final ICartService cartService;

	@Transactional
	public OrderDataDTO orchestrate(final CreateOrderRequest request) {
		final List<CartItemDTO> cartItems = Optional
			.ofNullable(this.cartService.fetchAllByCartIdAndAccountId(request.getCartId(), request.getAccountId()))
			.filter(items -> !items.isEmpty())
			.orElseThrow(() -> new InvalidRequestException("cartId", "No items found in cartId : %s",
					request.getCartId().toString()));

		final OrderDataDTO order = this.service.create(CreateOrderCommand.builder()
			.clientReferenceId(request.getClientReferenceId())
			.accountId(request.getAccountId())
			.customerId(request.getAccountId().toString())
			.customerType("BUYER")
			.items(cartItems.stream()
				.map(cartItem -> OrderItemDTO.builder()
					.clientReferenceId(
							UUIDUtils.uuid5(request.getClientReferenceId() + "_" + cartItem.getInventoryId()))
					.quantity(cartItem.getQuantity())
					.inventoryId(cartItem.getInventoryId())
					.build())
				.toList())
			.build());

		this.cartService.clear(cartItems.stream().map(CartItemDTO::getCartId).toList());
		return order;
	}

}
