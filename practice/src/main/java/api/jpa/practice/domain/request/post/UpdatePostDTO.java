package api.jpa.practice.domain.request.post;

import api.jpa.practice.domain.form.PostForm;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdatePostDTO {
    private String username;
    private String containerTitle;
    private String postTitle;
    private PostForm postForm;
}
