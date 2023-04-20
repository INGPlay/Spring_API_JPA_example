package api.jpa.practice.entity;

import api.jpa.practice.entity.embeddables.ContainerIds;
import api.jpa.practice.entity.embeddables.TimeInform;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Container {
    @EmbeddedId
    private ContainerIds containerIds;

    private String title;

    @Embedded
    private TimeInform timeInform;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user){
        this.user = user;
        user.getContainer().add(this);
    }

//    @OneToMany
//    @JoinColumn(name = "post_id")
//    List<Post> post = new ArrayList<>();

//    @OneToOne
//    @MapsId     // FK를 PK로
//    @JoinColumn(name = "container_keys")
//    private PostSequence postSequence;
}
