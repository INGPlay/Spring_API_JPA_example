package api.jpa.practice.apiController;

import api.jpa.practice.domain.request.*;
import api.jpa.practice.domain.form.PostForm;
import api.jpa.practice.domain.request.post.CreatePostDTO;
import api.jpa.practice.domain.request.post.PostPathDTO;
import api.jpa.practice.domain.request.post.UpdatePostDTO;
import api.jpa.practice.domain.request.container.ContainerPathDTO;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @ApiOperation(value = "모든 포스트 조회", notes = "해당 컨테이너의 모든 포스트 목록을 조회한다.")
    @GetMapping("/user/{username}/container/{containerTitle}/post-list")
    public ResponseWrapper getPosts(@PathVariable(name = "username") String username,
                                    @PathVariable(name = "containerTitle") String containerTitle){

        ContainerPathDTO containerPathDTO = new ContainerPathDTO();
        containerPathDTO.setUsername(username);
        containerPathDTO.setContainerTitle(containerTitle);

        return postService.findPosts(containerPathDTO);
    }

    @ApiOperation(value = "일부 포스트 조회(페이징)", notes = "해당 컨테이너의 포스트 목록 중 특정 범위를 조회한다.")
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

    @ApiOperation(value = "포스트 등록", notes = "포스트를 등록한다.")
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

    @ApiOperation(value = "포스트 조회", notes = "해당 포스트를 조회한다.")
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
    @ApiOperation(value = "포스트 검색", notes = "해당 유저의 컨테이너에서 제목에 키워드가 들어간 포스트를 조회한다.")
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

    @ApiOperation(value = "포스트 검색 (paging)", notes = "해당 유저의 컨테이너에서 제목에 키워드가 들어간 포스트 중 특정 범위를 조회한다.")
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
    @ApiOperation(value = "포스트 삭제", notes = "해당 포스트를 삭제한다.")
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
    @ApiOperation(value = "포스트 수정", notes = "해당 포스트를 수정한다.")
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
