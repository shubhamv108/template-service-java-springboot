package code.shubham.core.ordermodels;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class CreateOrderCommand {

	@NotNull
	private List<OrderItemDTO> items;

	@NotNull
	private Long accountId;

	@Min(10)
	@Max(36)
	private String customerId;

	@Min(10)
	@Max(36)
	private String customerType;

	@NotNull
	@NotEmpty
	@Min(36)
	@Max(36)
	private String clientReferenceId;

}
