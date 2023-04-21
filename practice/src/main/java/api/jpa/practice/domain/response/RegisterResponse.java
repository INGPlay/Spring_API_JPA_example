package api.jpa.practice.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterResponse {
    private boolean register;
    private boolean duplicate;

    public RegisterResponse() {
        this.duplicate = false;
    }
}
