package api.jpa.practice.domain.response.exception.exceptions.haveNot;

import api.jpa.practice.domain.response.exception.ErrorCode;
import api.jpa.practice.domain.response.exception.exceptions.abstracts.AbstractErrorCodeException;

public class UserHaveNotContainer extends AbstractErrorCodeException {
    @Override
    protected ErrorCode setErrorCode() {
        return ErrorCode.USER_HAVE_NOT_CONTAINER;
    }
}
