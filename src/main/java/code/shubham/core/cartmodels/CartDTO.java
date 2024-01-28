package code.shubham.core.cartmodels;

import lombok.Builder;

import java.util.List;

@Builder

public class CartDTO {

	private Long cartId;

	private List<AddCartItemRequest> cartItems;

}
