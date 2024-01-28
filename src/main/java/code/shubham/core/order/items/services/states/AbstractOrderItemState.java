package code.shubham.core.order.items.services.states;

import code.shubham.core.order.items.dao.entities.OrderItem;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuperBuilder
@Builder
@RequiredArgsConstructor
public abstract class AbstractOrderItemState {

	private final OrderItem item;

	public abstract void applyQuantityOperation(int incrementBy, String clientReferenceId);

	public abstract boolean markCompleted();

	public abstract boolean cancel();

	public abstract boolean initiateReturn();

	public abstract boolean returnSuccess();

}
