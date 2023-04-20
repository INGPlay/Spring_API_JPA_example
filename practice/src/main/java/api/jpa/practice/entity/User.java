package api.jpa.practice.entity;

import api.jpa.practice.entity.embeddables.TimeInform;
import api.jpa.practice.entity.enums.UserRole;
import api.jpa.practice.entity.sequenceTables.ContainerSequence;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Embedded
    private TimeInform timeInform;

    @OneToMany
    @JoinColumn(name = "container_id")
    private List<Container> containers = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private ContainerSequence containerSequence;

    public void addContainer(Container container){
        container.setUser(this);
        this.containers.add(container);
    }
}
