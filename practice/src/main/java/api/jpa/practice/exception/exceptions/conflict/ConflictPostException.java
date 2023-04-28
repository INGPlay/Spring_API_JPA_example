package api.jpa.practice.exception.exceptions.conflict;

import api.jpa.practice.exception.ErrorCode;
import api.jpa.practice.exception.exceptions.abstracts.AbstractErrorCodeException;

public class ConflictPostException extends AbstractErrorCodeException {

    @Override
    protected ErrorCode setErrorCode() {
        return ErrorCode.CONFLICT_POST;
    }
}
