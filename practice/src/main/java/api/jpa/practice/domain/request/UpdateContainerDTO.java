package api.jpa.practice.domain.request;

import api.jpa.practice.domain.form.SubmitContainerForm;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateContainerDTO {
    private Long targetContainerId;
    private SubmitContainerForm submitContainerForm;
}
