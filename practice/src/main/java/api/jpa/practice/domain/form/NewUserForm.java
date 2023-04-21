package api.jpa.practice.domain.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NewUserForm {
    private String newUsername;
    private String newPassword;
}
