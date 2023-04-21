package api.jpa.practice.domain.request;

import api.jpa.practice.entity.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterDTO {
    private String username;
    private String password;
    private UserRole userRole;
}
