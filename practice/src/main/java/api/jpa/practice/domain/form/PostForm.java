package api.jpa.practice.domain.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostForm {
    private String username;
    private String containerTitle;
    private String postTitle;
}
