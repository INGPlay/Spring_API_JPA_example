package api.jpa.practice.domain.request;

import api.jpa.practice.entity.Container;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostDTO {
    private Container container;
    private String title;
    private String content;
}
