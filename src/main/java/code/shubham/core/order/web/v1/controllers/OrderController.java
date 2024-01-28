package code.shubham.core.order.web.v1.controllers;

import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.core.order.orchestrator.CreateOrderOrchestrator;
import code.shubham.core.order.services.OrderService;
import code.shubham.core.order.web.v1.validators.CreateOrderRequestValidator;
import code.shubham.core.ordermodels.CreateOrderRequest;
import code.shubham.core.ordermodels.GetAllOrdersResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Order")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService service;

	private final ApplicationContext applicationContext;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody final CreateOrderRequest request) {
		new CreateOrderRequestValidator().validateOrThrowException(request);
		Utils.validateAccountOrThrowException(request.getAccountId());
		final CreateOrderOrchestrator orchestrator = this.applicationContext.getBean("CreateOrderOrchestrator",
				CreateOrderOrchestrator.class);
		return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED, orchestrator.orchestrate(request));
	}

	@GetMapping
	public ResponseEntity<?> getAllOrders(@RequestParam("accountId") final Long accountId) {
		Utils.validateAccountOrThrowException(accountId);
		return ResponseUtils.getDataResponseEntity(HttpStatus.FOUND,
				GetAllOrdersResponse.builder()
					.orders(this.service.fetchAllByAccountId(accountId)
						.stream()
						.map(order -> GetAllOrdersResponse.Order.builder()
							.orderId(order.getId())
							.status(order.getStatus().name())
							.accountId(order.getAccountId())
							.build())
						.toList())
					.build());
	}

}
