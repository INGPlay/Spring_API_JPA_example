package api.jpa.practice.entity.embeddables;

import api.jpa.practice.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter @Setter
public class ContainerIds implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "container_id")
    private Long containerId;

}
