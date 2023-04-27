package api.jpa.practice.config;

import api.jpa.practice.domain.form.PostForm;
import api.jpa.practice.domain.form.UserForm;
import api.jpa.practice.domain.request.container.ContainerPathDTO;
import api.jpa.practice.domain.request.post.CreatePostDTO;
import api.jpa.practice.domain.request.post.PostPathDTO;
import api.jpa.practice.service.ContainerService;
import api.jpa.practice.service.PostService;
import api.jpa.practice.service.ShortCutService;
import api.jpa.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class initializer {
    private final UserService userService;
    private final ContainerService containerService;
    private final PostService postService;
    private final ShortCutService shortCutService;

    @PostConstruct
    @Transactional
    public void init(){
        String username1 = "faraway";
        String username2 = "ingplay";

        UserForm userForm1 = new UserForm();
        userForm1.setUsername(username1);
        userForm1.setPassword("ffffffff");
        userService.insertUser(userForm1);

        UserForm userForm2 = new UserForm();
        userForm2.setUsername(username2);
        userForm2.setPassword("aaaaaaaaaa");
        userService.insertUser(userForm2);

        String containerTitle1 = "새 컨테이너";
        String containerTitle2 = "다른 컨테이너";
        String containerTitle3 = "new Title";

        ContainerPathDTO containerPathDTO1 = new ContainerPathDTO();
        containerPathDTO1.setUsername(username1);
        containerPathDTO1.setContainerTitle(containerTitle1);
        containerService.createContainer(containerPathDTO1);

        ContainerPathDTO containerPathDTO2 = new ContainerPathDTO();
        containerPathDTO2.setUsername(username1);
        containerPathDTO2.setContainerTitle(containerTitle2);
        containerService.createContainer(containerPathDTO2);

        ContainerPathDTO containerPathDTO3 = new ContainerPathDTO();
        containerPathDTO3.setUsername(username2);
        containerPathDTO3.setContainerTitle(containerTitle3);
        containerService.createContainer(containerPathDTO3);

        String postTitle1 = "새 포스트 입니다.";
        String postTitle2 = "새 포스트 입니다.2";
        String postTitle3 = "새 포스트 입니다.3";

        CreatePostDTO createPostDTO = new CreatePostDTO();
        createPostDTO.setUsername(username1);
        createPostDTO.setContainerTitle(containerTitle1);
        PostForm postForm = new PostForm();
        postForm.setPostTitle(postTitle1);
        postForm.setPostContent("포스트 내용입니다.");
        createPostDTO.setPostForm(postForm);

        postService.createPost(createPostDTO);

        CreatePostDTO createPostDTO2 = new CreatePostDTO();
        createPostDTO2.setUsername(username1);
        createPostDTO2.setContainerTitle(containerTitle1);
        PostForm postForm2 = new PostForm();
        postForm2.setPostTitle(postTitle2);
        postForm2.setPostContent("포스트 내용입니다.2");
        createPostDTO2.setPostForm(postForm2);

        postService.createPost(createPostDTO2);

        CreatePostDTO createPostDTO3 = new CreatePostDTO();
        createPostDTO3.setUsername(username1);
        createPostDTO3.setContainerTitle(containerTitle2);
        PostForm postForm3 = new PostForm();
        postForm3.setPostTitle(postTitle3);
        postForm3.setPostContent("포스트 내용입니다.3");
        createPostDTO3.setPostForm(postForm3);

        postService.createPost(createPostDTO3);

        PostPathDTO postPathDTO = new PostPathDTO();
        postPathDTO.setUsername(username1);
        postPathDTO.setConatainerTitle(containerTitle1);
        postPathDTO.setPostTitle(postTitle1);
        shortCutService.linkShortCut(postPathDTO);
    }
}
