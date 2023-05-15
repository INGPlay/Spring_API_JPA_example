package api.jpa.practice.entity;

import api.jpa.practice.entity.embeddables.TimeInform;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "container_id")
    private Container container;

    @JsonIgnore
    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ShortCut shortCut;

    @Embedded
    private TimeInform timeInform;

    private String title;
    private String content;

    public void setContainer(Container container){
        if (this.container != null){
            this.container.getPosts().remove(this);
        }
        this.container = container;
        container.getPosts().add(this);
    }
}
