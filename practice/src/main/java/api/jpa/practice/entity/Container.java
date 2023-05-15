package api.jpa.practice.entity;

import api.jpa.practice.entity.embeddables.TimeInform;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "container_id")
    private Long containerId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    private TimeInform timeInform;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    public Container(User user, TimeInform timeInform, String title) {
        setUser(user);
        this.timeInform = timeInform;
        this.title = title;
    }

    public void setUser(User user){
        if (this.user != null){
            this.user.getContainers().remove(this);
        }
        this.user = user;
        user.getContainers().add(this);
    }
}
