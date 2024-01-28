package code.shubham.core.ordermodels;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateOrderRequest {

	@NotNull
	private Long cartId;

	@NotNull
	@NotEmpty
	@Min(36)
	@Max(36)
	private String clientReferenceId;

	@NotNull
	private Long accountId;

}
