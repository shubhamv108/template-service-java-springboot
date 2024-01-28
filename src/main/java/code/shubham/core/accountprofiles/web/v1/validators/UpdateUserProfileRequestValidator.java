package code.shubham.core.accountprofiles.web.v1.validators;

import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;
import code.shubham.core.accountprofilemodels.UpdateUserProfileRequest;

public class UpdateUserProfileRequestValidator extends AbstractRequestValidator<UpdateUserProfileRequest> {

	@Override
	public IValidator<UpdateUserProfileRequest> validate(UpdateUserProfileRequest request) {
		super.validate(request);

		if (StringUtils.isEmpty(request.getAddress()))
			this.putMessage("address", MUST_NOT_BE_EMPTY, "address");

		return this;
	}

}
