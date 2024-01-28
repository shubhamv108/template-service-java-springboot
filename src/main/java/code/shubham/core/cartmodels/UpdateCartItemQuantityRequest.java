package code.shubham.core.cartmodels;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateCartItemQuantityRequest {

	private Integer incrementBy;

	private Long accountId;

}
