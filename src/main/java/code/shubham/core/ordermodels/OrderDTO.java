package code.shubham.core.ordermodels;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OrderDTO {

	@NotNull
	private Long orderId;

	@NotEmpty
	private String customerId;

	@NotEmpty
	private String customerType;

	@NotNull
	private Long accountId;

	@NotNull
	@NotEmpty
	private String status;

	@NotNull
	@NotEmpty
	@Min(3)
	@Max(36)
	private String uniqueReferenceId;

}
