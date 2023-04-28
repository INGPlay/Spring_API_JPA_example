package api.jpa.practice.exception.exceptions.haveNot;

import api.jpa.practice.exception.ErrorCode;
import api.jpa.practice.exception.exceptions.abstracts.AbstractErrorCodeException;

public class UserHaveNotContainer extends AbstractErrorCodeException {
    @Override
    protected ErrorCode setErrorCode() {
        return ErrorCode.USER_HAVE_NOT_CONTAINER;
    }
}
