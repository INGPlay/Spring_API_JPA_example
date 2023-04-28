package api.jpa.practice.exception.exceptions.abstracts;

import api.jpa.practice.exception.ErrorCode;
import lombok.Getter;

@Getter
abstract public class AbstractErrorCodeException extends RuntimeException {
    private final ErrorCode errorCode;

    public AbstractErrorCodeException() {
        this.errorCode = setErrorCode();
    }

    abstract protected ErrorCode setErrorCode();
}
