package code.shubham.core.order.items.web.v1.controllers;

import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.core.order.items.services.OrderItemService;
import code.shubham.core.order.services.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders/{orderId}/items")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "OrderItem")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
@RequiredArgsConstructor
public class OrderItemController {

	private final OrderService orderService;

	private final OrderItemService service;

	@GetMapping
	public ResponseEntity<?> getAllOrderItems(@PathVariable("orderId") final Long orderId,
			@RequestParam("accountId") final Long accountId) {
		Utils.validateAccountOrThrowException(accountId);
		this.orderService.fetchByUserIdAndOrderOrderId(accountId, orderId)
			.orElseThrow(
					() -> new InvalidRequestException("orderId", "No order found for oderId: %s", orderId.toString()));
		return ResponseUtils.getDataResponseEntity(HttpStatus.FOUND, this.service.fetchAllByOrderId(orderId));
	}

}
