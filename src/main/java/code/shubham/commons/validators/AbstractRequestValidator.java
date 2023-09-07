package code.shubham.commons.validators;

import code.shubham.common.exceptions.InvalidRequestException;

public abstract class AbstractRequestValidator<Request> extends code.shubham.commons.validators.AbstractValidator<Request> {
    @Override
    public code.shubham.commons.validators.IValidator<Request> validate(final Request request) {
        if (request == null) {
            this.putMessage("request", MUST_NOT_BE_EMPTY, "request");
            return this;
        }
        return this;
    }

    @Override
    public code.shubham.commons.validators.IValidator<Request> validateOrThrowException(final Request request) {
        this.validate(request);
        if (this.hasMessages()) {
            this.throwException();
        }
        return this;
    }

    protected void throwException() {
        throw new InvalidRequestException(this.getResult());
    }
}
