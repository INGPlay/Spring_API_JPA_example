package api.jpa.practice.domain.response.exception.exceptions.notFound;

import api.jpa.practice.domain.response.exception.ErrorCode;
import api.jpa.practice.domain.response.exception.exceptions.abstracts.AbstractErrorCodeException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundPostException extends AbstractErrorCodeException {
    @Override
    protected ErrorCode setErrorCode() {
        return ErrorCode.NOT_FOUND_POST;
    }
}
