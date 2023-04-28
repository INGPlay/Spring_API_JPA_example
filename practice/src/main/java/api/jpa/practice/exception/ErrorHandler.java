package api.jpa.practice.exception;

import api.jpa.practice.exception.exceptions.conflict.ConflictContainerException;
import api.jpa.practice.exception.exceptions.conflict.ConflictPostException;
import api.jpa.practice.exception.exceptions.conflict.ConflictShortCutException;
import api.jpa.practice.exception.exceptions.conflict.ConflictUserException;
import api.jpa.practice.exception.exceptions.haveNot.ContainerHaveNotPost;
import api.jpa.practice.exception.exceptions.haveNot.UserHaveNotContainer;
import api.jpa.practice.exception.exceptions.notFound.NotFoundContainerException;
import api.jpa.practice.exception.exceptions.notFound.NotFoundPostException;
import api.jpa.practice.exception.exceptions.notFound.NotFoundShortCut;
import api.jpa.practice.exception.exceptions.notFound.NotFoundUserException;
import api.jpa.practice.exception.exceptions.abstracts.AbstractErrorCodeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springfox.documentation.builders.ValidationResult;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {

    private final MessageSource messageSource;

    @ExceptionHandler({NotFoundUserException.class, NotFoundContainerException.class,
            NotFoundPostException.class, NotFoundShortCut.class})
    protected ResponseEntity<ErrorResponse<String>> NotFoundException(AbstractErrorCodeException exception){

        ErrorResponse<String> response = getErrorResponse(exception, "NotFoundException");

        return new ResponseEntity<>(response, exception.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler({ConflictUserException.class, ConflictContainerException.class,
            ConflictPostException.class, ConflictShortCutException.class})
    protected ResponseEntity<ErrorResponse<String>> ConflictException(AbstractErrorCodeException exception){

        ErrorResponse<String> response = getErrorResponse(exception, "ConflictException");

        return new ResponseEntity<>(response, exception.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler({UserHaveNotContainer.class, ContainerHaveNotPost.class})
    protected ResponseEntity<ErrorResponse<String>> HaveNotException(AbstractErrorCodeException exception){

        ErrorResponse<String> response = getErrorResponse(exception, "HaveNotException");

        return new ResponseEntity<>(response, exception.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<ErrorResponse<List<String>>> HaveNotException(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        log.info("{}", bindingResult);
        List<String> errorCollect = bindingResult.getAllErrors().stream()
                .map(e -> {
                    return messageSource.getMessage(e, null);
                })
                .collect(Collectors.toList());

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorResponse<List<String>> response = new ErrorResponse<>(httpStatus, errorCollect);

        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ErrorResponse<String>> handException(Exception exception){
        exception.printStackTrace();

        ErrorResponse<String> response = new ErrorResponse<>(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }


    private static ErrorResponse<String> getErrorResponse(AbstractErrorCodeException exception, String HaveNotException) {
        String errorMessage = exception.getErrorCode().getMessage();
        log.info(HaveNotException, errorMessage);

        return new ErrorResponse<String>(exception.getErrorCode().getHttpStatus(), errorMessage);
    }
}
