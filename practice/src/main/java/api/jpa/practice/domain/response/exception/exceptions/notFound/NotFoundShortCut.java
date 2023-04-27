package api.jpa.practice.domain.response.exception.exceptions.notFound;

import api.jpa.practice.domain.response.exception.ErrorCode;
import api.jpa.practice.domain.response.exception.exceptions.abstracts.AbstractErrorCodeException;

public class NotFoundShortCut extends AbstractErrorCodeException {

    @Override
    protected ErrorCode setErrorCode() {
        return ErrorCode.NOT_FOUND_SHORT_CUT;
    }
}
