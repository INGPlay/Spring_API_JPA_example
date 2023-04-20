package api.jpa.practice.domain.request;

import api.jpa.practice.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterForm {
    private String username;
    private String password;
    private UserRole userRole;
}
