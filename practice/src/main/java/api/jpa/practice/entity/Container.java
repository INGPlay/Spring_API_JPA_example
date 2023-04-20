package api.jpa.practice.entity;

import api.jpa.practice.entity.embeddables.ContainerIds;
import api.jpa.practice.entity.embeddables.TimeInform;
import api.jpa.practice.entity.sequenceTables.ContainerSequence;
import api.jpa.practice.entity.sequenceTables.PostSequence;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Container {
    @EmbeddedId
    private ContainerIds containerIds;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    private TimeInform timeInform;

    @Column(nullable = false, unique = true)
    private String title;

    @OneToMany
    @JoinColumns({
            @JoinColumn(name = "user_id"),
            @JoinColumn(name = "container_id")
    })
    private List<Post> posts = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "post_sequence_id")
    private PostSequence postSequence;

    public Container(User user, ContainerIds containerIds, TimeInform timeInform, String title) {
        this.user = user;
        this.containerIds = containerIds;
        this.timeInform = timeInform;
        this.title = title;
    }

    public void setUser(User user){
        this.user = user;
        user.getContainers().add(this);
    }
}
