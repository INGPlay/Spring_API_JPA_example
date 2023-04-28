package api.jpa.practice.exception.exceptions.haveNot;

import api.jpa.practice.exception.ErrorCode;
import api.jpa.practice.exception.exceptions.abstracts.AbstractErrorCodeException;

public class ContainerHaveNotPost extends AbstractErrorCodeException {
    @Override
    protected ErrorCode setErrorCode() {
        return ErrorCode.CONTAINER_HAVE_NOT_POST;
    }
}
