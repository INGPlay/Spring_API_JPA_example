package api.jpa.practice.domain.request.post;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostPathDTO {
    private String username;
    private String conatainerTitle;
    private String postTitle;
}
