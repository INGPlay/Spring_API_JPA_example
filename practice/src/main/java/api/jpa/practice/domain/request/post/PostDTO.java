package api.jpa.practice.domain.request.post;

import api.jpa.practice.domain.form.PostForm;
import api.jpa.practice.entity.Container;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostDTO {
    private Container container;
    private PostForm postForm;
}
