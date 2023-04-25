package api.jpa.practice.domain.request;

import api.jpa.practice.domain.form.ContainerForm;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateContainerDTO {
    private String username;
    private String containerTitle;
    private ContainerForm containerForm;
}
