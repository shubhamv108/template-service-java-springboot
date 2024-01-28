package code.shubham.core.cartcommons;

import code.shubham.core.cartmodels.CartItemDTO;

import java.util.Collection;
import java.util.List;

public interface ICartService {

	List<CartItemDTO> fetchAllByCartIdAndAccountId(Long cartId, Long accountId);

	void clear(Collection<Long> itemIds);

}
