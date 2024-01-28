package code.shubham.core.cartmodels;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CartItemDTO {

	private Long cartItemId;

	private Long cartId;

	private Long inventoryId;

	private int quantity;

}
