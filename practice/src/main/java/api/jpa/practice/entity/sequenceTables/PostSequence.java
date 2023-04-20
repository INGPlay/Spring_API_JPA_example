package api.jpa.practice.entity.sequenceTables;

import api.jpa.practice.entity.Container;
import api.jpa.practice.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "POST_SEQ")
@Getter @Setter
@NoArgsConstructor
public class PostSequence {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_sequence_id")
    private Long id;

    @Column(nullable = false)
    private Long sequenceIndex = 0L;

    @OneToOne(mappedBy = "postSequence")
    private Container container;
    public PostSequence(Long sequenceIndex) {
        this.sequenceIndex = sequenceIndex;
    }
}
