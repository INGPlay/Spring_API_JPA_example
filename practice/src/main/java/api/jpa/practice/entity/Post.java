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
    @EmbeddedId
    private PostIds postIds;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("containerIds")     // 빨간줄 무시하기, 부모의 id가 들어가야 되므로 이게 맞음.
    @JoinColumns({
            @JoinColumn(name = "user_id"),
            @JoinColumn(name = "container_id")
    })
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
