package api.jpa.practice.domain.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
public class UserForm {
    @NotNull
    @Size(min = 4, max = 10)
    @Pattern(regexp = "^([a-z0-9]*)$")
    private String username;

    @NotNull
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^([A-Za-z0-9!@#$%]*)$")
    private String password;
}
