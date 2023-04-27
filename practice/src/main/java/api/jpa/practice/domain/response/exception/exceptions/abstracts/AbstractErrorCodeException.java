package api.jpa.practice.domain.response.exception.exceptions.abstracts;

import api.jpa.practice.domain.response.exception.ErrorCode;
import lombok.Getter;

@Getter
abstract public class AbstractErrorCodeException extends RuntimeException {
    private final ErrorCode errorCode;

    public AbstractErrorCodeException() {
        this.errorCode = setErrorCode();
    }

    abstract protected ErrorCode setErrorCode();
}
