package api.jpa.practice.domain.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class PostForm {

    @NotEmpty
    @Size(max = 50)
    private String postTitle;

    @Size(max = 500)
    private String postContent;
}
