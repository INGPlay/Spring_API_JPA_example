package api.jpa.practice.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostForm {
    private Long userId;
    private Long containerId;
    private String title;
    private String content;
}
