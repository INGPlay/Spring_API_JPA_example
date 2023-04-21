package api.jpa.practice.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWrapper {
    private Object object;
    private String errorMessage;
}