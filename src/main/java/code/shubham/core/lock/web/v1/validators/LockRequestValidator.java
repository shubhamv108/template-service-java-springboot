package code.shubham.core.lock.web.v1.validators;

import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;
import code.shubham.core.lockmodels.LockRequestDTO;

import java.util.Objects;

public class LockRequestValidator extends AbstractRequestValidator<LockRequestDTO> {

	@Override
	public IValidator<LockRequestDTO> validate(final LockRequestDTO request) {
		super.validate(request);

		if (StringUtils.isEmpty(request.getOwner()))
			this.putMessage("owner", MUST_NOT_BE_EMPTY, "version");

		if (Objects.isNull(request.getPreviousVersion()))
			this.putMessage("previousVersion", MUST_NOT_BE_EMPTY, "previousVersion");

		if (Objects.isNull(request.getTimeToLiveInSeconds()))
			this.putMessage("timeToLiveInSeconds", MUST_NOT_BE_EMPTY, "timeToLiveInSeconds");

		return this;
	}

}
