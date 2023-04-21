package api.jpa.practice.domain.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterForm {
    private String username;
    private String password;
}
