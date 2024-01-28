package code.shubham.core.order.web.v1.validators;

import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;
import code.shubham.core.ordermodels.CreateOrderRequest;

import static code.shubham.commons.validators.IValidator.MUST_NOT_BE_EMPTY;

public class CreateOrderRequestValidator extends AbstractRequestValidator<CreateOrderRequest> {

	@Override
	public IValidator<CreateOrderRequest> validate(final CreateOrderRequest request) {
		super.validate(request);

		if (this.isNotValidId(request.getAccountId()))
			this.putMessage("userId", MUST_NOT_BE_EMPTY, "userId");
		if (this.isNotValidStringId(request.getClientReferenceId()))
			this.putMessage("clientReferenceId", MUST_NOT_BE_EMPTY, "clientReferenceId");
		if (this.isNotValidId(request.getCartId()))
			this.putMessage("cartId", MUST_NOT_BE_EMPTY, "cartId");

		return this;
	}

}
