package api.jpa.practice.domain.request.container;

import api.jpa.practice.domain.form.ContainerForm;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateContainerDTO {
    private ContainerPathDTO containerPathDTO;
    private ContainerForm containerForm;
}
