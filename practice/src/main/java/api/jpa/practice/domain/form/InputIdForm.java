package api.jpa.practice.domain.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class InputIdForm {
    @NotNull
    private Long shortCutId;
}
