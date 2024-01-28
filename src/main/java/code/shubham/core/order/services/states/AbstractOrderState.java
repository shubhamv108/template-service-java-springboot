package code.shubham.core.order.services.states;

import code.shubham.core.order.dao.entities.Order;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuperBuilder
@RequiredArgsConstructor
public abstract class AbstractOrderState {

	private final Order order;

	public abstract void makePayment();

	public abstract boolean markCompleted();

	public abstract boolean cancel();

	public abstract boolean initiateRefund();

	public abstract boolean refund();

}
