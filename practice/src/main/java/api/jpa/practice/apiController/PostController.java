package api.jpa.practice.apiController;

import api.jpa.practice.domain.request.*;
import api.jpa.practice.domain.form.PostForm;
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

        ContainerPathDTO containerPathDTO = new ContainerPathDTO();
        containerPathDTO.setUsername(username);
        containerPathDTO.setContainerTitle(containerTitle);

        return postService.findPosts(containerPathDTO);
    }

    @GetMapping("/user/{username}/container/{containerTitle}/post-list/{startPos}/{length}")
    public ResponseWrapper getPostsPaging(@PathVariable(name = "username") String username,
                                          @PathVariable(name = "containerTitle") String containerTitle,
                                          @PathVariable(name = "startPos") int startPos,
                                          @PathVariable(name = "length") int length){

        ContainerPathDTO containerPathDTO = new ContainerPathDTO();
        containerPathDTO.setUsername(username);
        containerPathDTO.setContainerTitle(containerTitle);

        return postService.findPosts(containerPathDTO, new PagingDTO(startPos, length));
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

    @GetMapping("/user/{username}/container/{containerTitle}/post-list/{postTitle}/{startPos}/{length}")
    public ResponseWrapper searchPostsPaging(@PathVariable(name = "username") String username,
                                             @PathVariable(name = "containerTitle") String containerTitle,
                                             @PathVariable(name = "postTitle") String postTitle,
                                             @PathVariable(name = "startPos") int startPos,
                                             @PathVariable(name = "length") int length){
        PostPathDTO postPathDTO = new PostPathDTO();
        postPathDTO.setUsername(username);
        postPathDTO.setConatainerTitle(containerTitle);
        postPathDTO.setPostTitle(postTitle);

        return postService.searchPosts(postPathDTO, new PagingDTO(startPos, length));
    }

    @DeleteMapping("/user/{username}/container/{containerTitle}/post/{postTitle}")
    public ResponseWrapper deletePost(@PathVariable(name = "username") String username,
                                      @PathVariable(name = "containerTitle") String containerTitle,
                                      @PathVariable(name = "postTitle") String postTitle){

        PostPathDTO postPathDTO = new PostPathDTO();
        postPathDTO.setUsername(username);
        postPathDTO.setConatainerTitle(containerTitle);
        postPathDTO.setPostTitle(postTitle);

        return postService.deletePost(postPathDTO);
    }

    @PutMapping("/user/{username}/container/{containerTitle}/post/{postTitle}")
    public ResponseWrapper updatePost(@PathVariable(name = "username") String username,
                                      @PathVariable(name = "containerTitle") String containerTitle,
                                      @PathVariable(name = "postTitle") String postTitle,
                                      @RequestBody PostForm postForm){

        UpdatePostDTO updatePostDTO = new UpdatePostDTO();
        updatePostDTO.setUsername(username);
        updatePostDTO.setContainerTitle(containerTitle);
        updatePostDTO.setPostTitle(postTitle);
        updatePostDTO.setPostForm(postForm);

        return postService.updatePost(updatePostDTO);
    }
}
