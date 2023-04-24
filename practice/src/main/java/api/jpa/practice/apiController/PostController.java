package api.jpa.practice.apiController;

import api.jpa.practice.domain.form.ContainerForm;
import api.jpa.practice.domain.form.PostForm;
import api.jpa.practice.domain.form.UpdatePostForm;
import api.jpa.practice.domain.request.CreatePostDTO;
import api.jpa.practice.domain.request.PostPathDTO;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/user/{username}/container/{containerTitle}/post-list")
    public ResponseWrapper getPosts(@PathVariable(name = "username") String username,
                                    @PathVariable(name = "containerTitle") String containerTitle){

        ContainerForm containerForm = new ContainerForm();
        containerForm.setUsername(username);
        containerForm.setContainerTitle(containerTitle);

        return postService.findPosts(containerForm);
    }

    @PostMapping("/user/{username}/container/{containerTitle}/post")
    public ResponseWrapper createPost(@PathVariable(name = "username") String username,
                                      @PathVariable(name = "containerTitle") String containerTitle,
                                      @RequestBody PostForm postForm){
        CreatePostDTO createPostDTO = new CreatePostDTO();
        createPostDTO.setUsername(username);
        createPostDTO.setContainerTitle(containerTitle);
        createPostDTO.setPostForm(postForm);

        return postService.createPost(createPostDTO);
    }

    @GetMapping("/user/{username}/container/{containerTitle}/post/{postTitle}")
    public ResponseWrapper getPost(@PathVariable(name = "username") String username,
                                   @PathVariable(name = "containerTitle") String containerTitle,
                                   @PathVariable(name = "postTitle") String postTitle){
        PostPathDTO postPathDTO = new PostPathDTO();
        postPathDTO.setUsername(username);
        postPathDTO.setConatainerTitle(containerTitle);
        postPathDTO.setPostTitle(postTitle);

        return postService.findPost(postPathDTO);
    }

    @GetMapping("/user/{username}/container/{containerTitle}/post-list/{postTitle}")
    public ResponseWrapper searchPosts(@PathVariable(name = "username") String username,
                                       @PathVariable(name = "containerTitle") String containerTitle,
                                       @PathVariable(name = "postTitle") String postTitle){
        PostPathDTO postPathDTO = new PostPathDTO();
        postPathDTO.setUsername(username);
        postPathDTO.setConatainerTitle(containerTitle);
        postPathDTO.setPostTitle(postTitle);

        return postService.searchPosts(postPathDTO);
    }

    @DeleteMapping("/user/{username}/container/{containerTitle}/post/{postTitle}")
    public ResponseWrapper deletePost(@PathVariable PostForm postForm){
        return new ResponseWrapper();
    }

    @PutMapping("/user/{username}/container/{containerTitle}/post/{postTitle}")
    public ResponseWrapper updatePost(@RequestBody UpdatePostForm updatePostForm){
        return new ResponseWrapper();
    }
}
