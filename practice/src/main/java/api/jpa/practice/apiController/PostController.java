package api.jpa.practice.apiController;

import api.jpa.practice.domain.form.ContainerForm;
import api.jpa.practice.domain.form.PostForm;
import api.jpa.practice.domain.form.UpdatePostForm;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.repository.PostRepostiory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostRepostiory postRepostiory;

    @GetMapping("/user/{username}/container/{containerTitle}/post")
    public ResponseWrapper getPosts(@PathVariable ContainerForm containerForm){
        return new ResponseWrapper();
    }

    @PostMapping("/user/{username}/container/{containerTitle}/post/{postTitle}")
    public ResponseWrapper createContainer(@PathVariable PostForm postForm){
        return new ResponseWrapper();
    }

    @GetMapping("/user/{username}/container/{containerTitle}/post/{postTitle}")
    public ResponseWrapper getContainer(@PathVariable PostForm postForm){
        return new ResponseWrapper();
    }

    @DeleteMapping("/user/{username}/container/{containerTitle}/post/{postTitle}")
    public ResponseWrapper deleteContainer(@PathVariable PostForm postForm){
        return new ResponseWrapper();
    }

    @PutMapping("/user/{username}/container/{containerTitle}/post/{postTitle}")
    public ResponseWrapper updateContainer(@RequestBody UpdatePostForm updatePostForm){
        return new ResponseWrapper();
    }
}
