package api.jpa.practice.service;

import api.jpa.practice.domain.request.container.ContainerPathDTO;
import api.jpa.practice.domain.form.PostForm;
import api.jpa.practice.domain.request.*;
import api.jpa.practice.domain.request.post.CreatePostDTO;
import api.jpa.practice.domain.request.post.PostDTO;
import api.jpa.practice.domain.request.post.PostPathDTO;
import api.jpa.practice.domain.request.post.UpdatePostDTO;
import api.jpa.practice.domain.request.container.ContainerDTO;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.domain.response.exception.exceptions.conflict.ConflictPostException;
import api.jpa.practice.domain.response.exception.exceptions.haveNot.ContainerHaveNotPost;
import api.jpa.practice.domain.response.exception.exceptions.haveNot.UserHaveNotContainer;
import api.jpa.practice.entity.Container;
import api.jpa.practice.entity.Post;
import api.jpa.practice.entity.User;
import api.jpa.practice.repository.ContainerRepostiory;
import api.jpa.practice.repository.PostRepostiory;
import api.jpa.practice.repository.UserRepository;
import api.jpa.practice.service.component.ResultSupporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepostiory postRepostiory;
    private final ContainerRepostiory containerRepostiory;
    private final ResultSupporter resultSupporter;

    public ResponseWrapper findPosts(ContainerPathDTO containerPathDTO){

        ResponseWrapper responseWrapper = new ResponseWrapper();

        User userResult = resultSupporter.getUserResult(containerPathDTO.getUsername());

        List<Container> containers = containerRepostiory.findContainersByUser(userResult);

        if (containers.size() <= 0){
            throw new UserHaveNotContainer();
        }

        List<Post> posts = postRepostiory.findPostsByContainerForm(containerPathDTO);

        if (posts.size() <= 0){
            throw new ContainerHaveNotPost();
        }

        responseWrapper.setObject(posts);
        return responseWrapper;
    }

    public ResponseWrapper findPosts(ContainerPathDTO containerPathDTO, PagingDTO pagingDTO){

        ResponseWrapper responseWrapper = new ResponseWrapper();

        User userResult = resultSupporter.getUserResult(containerPathDTO.getUsername());

        List<Container> containers = containerRepostiory.findContainersByUser(userResult);

        if (containers.size() <= 0){
            throw new UserHaveNotContainer();
        }

        List<Post> posts = postRepostiory.findPostsByContainerForm(containerPathDTO, pagingDTO);

        if (posts.size() <= 0){
            throw new ContainerHaveNotPost();
        }

        responseWrapper.setObject(posts);
        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper findPost(PostPathDTO postPathDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = postPathDTO.getUsername();
        String containerTitle = postPathDTO.getConatainerTitle();
        String postTitle = postPathDTO.getPostTitle();

        User userResult = resultSupporter.getUserResult(username);

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(containerDTO);

        Post postResult = resultSupporter.getPostResult(containerResult, postTitle);

        responseWrapper.setObject(postResult);
        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper searchPosts(PostPathDTO postPathDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = postPathDTO.getUsername();
        String containerTitle = postPathDTO.getConatainerTitle();
        String postTitle = postPathDTO.getPostTitle();
        User userResult = resultSupporter.getUserResult(username);

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(containerDTO);

        List<Post> posts = postRepostiory.searchPosts(containerResult, postTitle);

        responseWrapper.setObject(posts);
        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper searchPosts(PostPathDTO postPathDTO, PagingDTO pagingDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = postPathDTO.getUsername();
        String containerTitle = postPathDTO.getConatainerTitle();
        String postTitle = postPathDTO.getPostTitle();
        User userResult = resultSupporter.getUserResult(username);

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(containerDTO);

        List<Post> posts = postRepostiory.searchPosts(containerResult, postTitle, pagingDTO);

        responseWrapper.setObject(posts);
        return responseWrapper;
    }


    @Transactional
    public ResponseWrapper createPost(CreatePostDTO createPostDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = createPostDTO.getUsername();
        String containerTitle = createPostDTO.getContainerTitle();
        PostForm postForm = createPostDTO.getPostForm();

        User userResult = resultSupporter.getUserResult(username);

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(containerDTO);

        Optional<Post> optionalPost = postRepostiory.findPost(containerResult, postForm.getPostTitle());
        if (optionalPost.isPresent()){
            throw new ConflictPostException();
        }

        PostDTO postDTO = new PostDTO();
        postDTO.setContainer(containerResult);
        postDTO.setPostForm(postForm);
        boolean isInsertedTemp = postRepostiory.insertPostByPostForm(postDTO);

        responseWrapper.setObject(new Object(){
            public final Boolean isInserted = isInsertedTemp;
        });

        return responseWrapper;

    }

    public ResponseWrapper deletePost(PostPathDTO postPathDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = postPathDTO.getUsername();
        String containerTitle = postPathDTO.getConatainerTitle();
        String postTitle = postPathDTO.getPostTitle();

        User userResult = resultSupporter.getUserResult(username);

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(containerDTO);

        Post postResult = resultSupporter.getPostResult(containerResult, postTitle);

        boolean isDeletedTemp = postRepostiory.deletePost(postResult);

        responseWrapper.setObject(new Object(){
            public final boolean isDeleted = isDeletedTemp;
        });

        return responseWrapper;
    }


    public ResponseWrapper updatePost(UpdatePostDTO updatePostDTO) {
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = updatePostDTO.getUsername();
        String containerTitle = updatePostDTO.getContainerTitle();
        String postTitle = updatePostDTO.getPostTitle();
        PostForm postForm = updatePostDTO.getPostForm();

        User userResult = resultSupporter.getUserResult(username);

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(containerDTO);

        Post postResult = resultSupporter.getPostResult(containerResult, postTitle);

        boolean isUpdatedTemp = postRepostiory.updatePost(postResult, postForm);

        responseWrapper.setObject(new Object(){
            public final boolean isUpdated = isUpdatedTemp;
        });
        return responseWrapper;
    }
}
