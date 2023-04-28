package api.jpa.practice.domain.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class ContainerForm {
    @NotEmpty
    @Size(max = 50)
    private String containerTitle;
}
