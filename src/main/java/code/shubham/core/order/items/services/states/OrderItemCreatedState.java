package code.shubham.core.order.items.services.states;

import code.shubham.core.order.items.dao.entities.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Builder
public class OrderItemCreatedState extends AbstractOrderItemState {

	@Override
	public void applyQuantityOperation(int incrementBy, String clientReferenceId) {

	}

	@Override
	public boolean markCompleted() {
		return false;
	}

	@Override
	public boolean cancel() {
		return false;
	}

	@Override
	public boolean initiateReturn() {
		return false;
	}

	@Override
	public boolean returnSuccess() {
		return false;
	}

}
