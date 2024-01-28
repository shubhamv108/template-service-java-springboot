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
public class OrderItemDTO {

	@NotNull
	@NotEmpty
	private Long inventoryId;

	@NotNull
	@NotEmpty
	private int quantity;

	@NotNull
	@NotEmpty
	@Min(3)
	@Max(64)
	private String status;

	@NotNull
	@NotEmpty
	@Min(16)
	@Max(36)
	private String clientReferenceId;

}
