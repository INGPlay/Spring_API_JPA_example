package api.jpa.practice.domain.response.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ErrorResponse {
    private HttpStatus httpStatus;
    private String errorMessage;
}
