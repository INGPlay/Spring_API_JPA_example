package api.jpa.practice.domain.response.exception.exceptions.haveNot;

import api.jpa.practice.domain.response.exception.ErrorCode;
import api.jpa.practice.domain.response.exception.exceptions.abstracts.AbstractErrorCodeException;

public class ContainerHaveNotPost extends AbstractErrorCodeException {
    @Override
    protected ErrorCode setErrorCode() {
        return ErrorCode.CONTAINER_HAVE_NOT_POST;
    }
}
