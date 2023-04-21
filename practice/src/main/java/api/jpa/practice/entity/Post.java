package api.jpa.practice.entity;

import api.jpa.practice.entity.embeddables.ContainerIds;
import api.jpa.practice.entity.embeddables.PostIds;
import api.jpa.practice.entity.embeddables.TimeInform;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.swing.border.TitledBorder;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "container_id")
    private Container container;

    @Embedded
    private TimeInform timeInform;

    private String title;
    private String content;

    public void setContainer(Container container){
        this.container = container;
        container.getPosts().add(this);
    }
}
