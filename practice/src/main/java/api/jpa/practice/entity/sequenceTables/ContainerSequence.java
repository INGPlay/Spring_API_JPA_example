package api.jpa.practice.entity.sequenceTables;

import api.jpa.practice.entity.User;
import api.jpa.practice.entity.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "Container_SEQ")
@Getter @Setter
@NoArgsConstructor
public class ContainerSequence {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private Long sequenceIndex;

    @OneToOne(mappedBy = "containerSequence")
    private User user;
    public ContainerSequence(Long sequenceIndex) {
        this.sequenceIndex = sequenceIndex;
    }
}
