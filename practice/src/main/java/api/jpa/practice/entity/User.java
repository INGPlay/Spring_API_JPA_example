package api.jpa.practice.entity;

import api.jpa.practice.entity.embeddables.TimeInform;
import api.jpa.practice.entity.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Container> containers = new ArrayList<>();

    public User(String username, String password, UserRole userRole, TimeInform timeInform) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.timeInform = timeInform;
    }

    public void addContainer(Container container){
        container.setUser(this);
        this.containers.add(container);
    }
}
