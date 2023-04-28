package api.jpa.practice.exception.exceptions.notFound;

import api.jpa.practice.exception.ErrorCode;
import api.jpa.practice.exception.exceptions.abstracts.AbstractErrorCodeException;

public class NotFoundShortCut extends AbstractErrorCodeException {

    @Override
    protected ErrorCode setErrorCode() {
        return ErrorCode.NOT_FOUND_SHORT_CUT;
    }
}
