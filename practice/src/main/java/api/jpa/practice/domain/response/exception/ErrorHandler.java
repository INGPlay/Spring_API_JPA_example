package api.jpa.practice.domain.response.exception;

import api.jpa.practice.domain.response.exception.exceptions.conflict.ConflictContainerException;
import api.jpa.practice.domain.response.exception.exceptions.conflict.ConflictPostException;
import api.jpa.practice.domain.response.exception.exceptions.conflict.ConflictShortCutException;
import api.jpa.practice.domain.response.exception.exceptions.conflict.ConflictUserException;
import api.jpa.practice.domain.response.exception.exceptions.haveNot.ContainerHaveNotPost;
import api.jpa.practice.domain.response.exception.exceptions.haveNot.UserHaveNotContainer;
import api.jpa.practice.domain.response.exception.exceptions.notFound.NotFoundContainerException;
import api.jpa.practice.domain.response.exception.exceptions.notFound.NotFoundPostException;
import api.jpa.practice.domain.response.exception.exceptions.notFound.NotFoundShortCut;
import api.jpa.practice.domain.response.exception.exceptions.notFound.NotFoundUserException;
import api.jpa.practice.domain.response.exception.exceptions.abstracts.AbstractErrorCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({NotFoundUserException.class, NotFoundContainerException.class,
            NotFoundPostException.class, NotFoundShortCut.class})
    protected ResponseEntity<ErrorResponse> NotFoundException(AbstractErrorCodeException exception){

        ErrorResponse response = getErrorResponse(exception, "NotFoundException");

        return new ResponseEntity<>(response, exception.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler({ConflictUserException.class, ConflictContainerException.class,
            ConflictPostException.class, ConflictShortCutException.class})
    protected ResponseEntity<ErrorResponse> ConflictException(AbstractErrorCodeException exception){

        ErrorResponse response = getErrorResponse(exception, "ConflictException");

        return new ResponseEntity<>(response, exception.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler({UserHaveNotContainer.class, ContainerHaveNotPost.class})
    protected ResponseEntity<ErrorResponse> HaveNotException(AbstractErrorCodeException exception){

        ErrorResponse response = getErrorResponse(exception, "HaveNotException");

        return new ResponseEntity<>(response, exception.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ErrorResponse> handException(Exception exception){
        exception.printStackTrace();

        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }


    private static ErrorResponse getErrorResponse(AbstractErrorCodeException exception, String HaveNotException) {
        String errorMessage = exception.getErrorCode().getMessage();
        log.info(HaveNotException, errorMessage);

        ErrorResponse response = new ErrorResponse(exception.getErrorCode().getHttpStatus(), errorMessage);
        return response;
    }
}
