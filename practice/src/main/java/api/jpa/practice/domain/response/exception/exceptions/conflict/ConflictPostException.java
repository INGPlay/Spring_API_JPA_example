package api.jpa.practice.domain.response.exception.exceptions.conflict;

import api.jpa.practice.domain.response.exception.ErrorCode;
import api.jpa.practice.domain.response.exception.exceptions.abstracts.AbstractErrorCodeException;

public class ConflictPostException extends AbstractErrorCodeException {

    @Override
    protected ErrorCode setErrorCode() {
        return ErrorCode.CONFLICT_POST;
    }
}
