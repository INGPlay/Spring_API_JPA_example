package api.jpa.practice.domain.response;

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
