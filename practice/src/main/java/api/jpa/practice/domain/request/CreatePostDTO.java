package api.jpa.practice.domain.request;

import api.jpa.practice.domain.form.PostForm;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreatePostDTO {
    private String username;
    private String containerTitle;
    private PostForm postForm;
}
