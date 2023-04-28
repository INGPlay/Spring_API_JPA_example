package api.jpa.practice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ErrorResponse<T> {
    private HttpStatus httpStatus;
    private T errorMessage;
}
