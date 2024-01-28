package code.shubham.core.cartmodels;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateCartRequest {

	private Long accountId;

}
