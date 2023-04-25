package api.jpa.practice.domain.request;

import api.jpa.practice.entity.Post;
import api.jpa.practice.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ShortCutDTO {
    private User user;
    private Post post;
}
