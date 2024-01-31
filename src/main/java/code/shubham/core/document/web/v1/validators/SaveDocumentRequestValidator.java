package code.shubham.core.document.web.v1.validators;

import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;
import code.shubham.core.documentmodels.SaveDocumentRequest;

public class SaveDocumentRequestValidator extends AbstractRequestValidator<SaveDocumentRequest> {

	@Override
	public IValidator<SaveDocumentRequest> validate(final SaveDocumentRequest request) {
		super.validate(request);
		if (StringUtils.isEmpty(request.getName()))
			this.putMessage("name", MUST_NOT_BE_EMPTY, "name");
		if (this.isNotValidId(request.getBlobId()))
			this.putMessage("blobId", MUST_BE_VALID_ID, "blobId");
		if (this.isNotValidId(request.getAccountId()))
			this.putMessage("accountId", MUST_BE_VALID_ID, "accountId");
		return this;
	}

}
