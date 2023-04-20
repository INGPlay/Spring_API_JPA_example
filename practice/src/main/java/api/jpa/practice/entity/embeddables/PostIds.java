package api.jpa.practice.entity.embeddables;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter @Setter
@EqualsAndHashCode
public class PostIds implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "container_id")
    private Long containerId;

    @Column(name = "post_id")
    private Long postId;
}
