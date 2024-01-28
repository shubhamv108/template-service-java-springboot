package code.shubham.core.documentstore.web.v1.validators;

import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;
import code.shubham.core.documentstoremodels.SaveDocumentRequest;

public class SaveDocumentRequestValidator extends AbstractRequestValidator<SaveDocumentRequest> {

	@Override
	public IValidator<SaveDocumentRequest> validate(final SaveDocumentRequest request) {
		super.validate(request);
		if (StringUtils.isEmpty(request.getName()))
			this.putMessage("name", MUST_NOT_BE_EMPTY, "name");
		if (null == request.getBlobId())
			this.putMessage("blobId", MUST_NOT_BE_EMPTY, "blobId");
		if (null == request.getBlobId())
			this.putMessage("userId", MUST_NOT_BE_EMPTY, "userId");
		return this;
	}

}
