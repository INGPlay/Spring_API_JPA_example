package api.jpa.practice.service;

import api.jpa.practice.domain.request.ContainerPathDTO;
import api.jpa.practice.domain.form.PostForm;
import api.jpa.practice.domain.request.*;
import api.jpa.practice.domain.response.ResponseWrapper;
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

        User userResult = resultSupporter.getUserResult(responseWrapper, containerPathDTO.getUsername());
        if (userResult == null){
            return responseWrapper;
        }

        List<Container> containers = containerRepostiory.findContainersByUser(userResult);

        if (containers.size() <= 0){
            responseWrapper.setErrorMessage("대상 유저가 컨테이너를 가지고 있지 않습니다.");
            return responseWrapper;
        }

        List<Post> posts = postRepostiory.findPostsByContainerForm(containerPathDTO);

        if (posts.size() <= 0){
            responseWrapper.setErrorMessage("컨테이너가 포스트를 가지고 있지 않습니다.");
            return responseWrapper;
        }

        responseWrapper.setObject(posts);
        return responseWrapper;
    }

    public ResponseWrapper findPosts(ContainerPathDTO containerPathDTO, PagingDTO pagingDTO){

        ResponseWrapper responseWrapper = new ResponseWrapper();

        User userResult = resultSupporter.getUserResult(responseWrapper, containerPathDTO.getUsername());
        if (userResult == null){
            return responseWrapper;
        }

        List<Container> containers = containerRepostiory.findContainersByUser(userResult);

        if (containers.size() <= 0){
            responseWrapper.setErrorMessage("대상 유저가 컨테이너를 가지고 있지 않습니다.");
            return responseWrapper;
        }

        List<Post> posts = postRepostiory.findPostsByContainerForm(containerPathDTO, pagingDTO);

        if (posts.size() <= 0){
            responseWrapper.setErrorMessage("컨테이너가 포스트를 가지고 있지 않습니다.");
            return responseWrapper;
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

        User userResult = resultSupporter.getUserResult(responseWrapper, username);
        if(userResult == null){
            return responseWrapper;
        }

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(responseWrapper, containerDTO);
        if(containerResult == null){
            return responseWrapper;
        }

        Post postResult = resultSupporter.getPostResult(responseWrapper, containerResult, postTitle);
        if(postResult == null){
            return responseWrapper;
        }

        responseWrapper.setObject(postResult);
        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper searchPosts(PostPathDTO postPathDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = postPathDTO.getUsername();
        String containerTitle = postPathDTO.getConatainerTitle();
        String postTitle = postPathDTO.getPostTitle();
        User userResult = resultSupporter.getUserResult(responseWrapper, username);
        if(userResult == null){
            return responseWrapper;
        }

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(responseWrapper, containerDTO);
        if(containerResult == null){
            return responseWrapper;
        }

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
        User userResult = resultSupporter.getUserResult(responseWrapper, username);
        if(userResult == null){
            return responseWrapper;
        }

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(responseWrapper, containerDTO);
        if(containerResult == null){
            return responseWrapper;
        }

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

        User userResult = resultSupporter.getUserResult(responseWrapper, username);
        if(userResult == null){
            return responseWrapper;
        }

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(responseWrapper, containerDTO);
        if(containerResult == null){
            return responseWrapper;
        }

        Optional<Post> optionalPost = postRepostiory.findPost(containerResult, postForm.getPostTitle());
        if (optionalPost.isPresent()){
            responseWrapper.setErrorMessage("같은 제목의 포스트가 이미 존재합니다.");
            return responseWrapper;
        }

        PostDTO postDTO = new PostDTO();
        postDTO.setContainer(containerResult);
        postDTO.setPostForm(postForm);
        boolean isInsertedTemp = postRepostiory.insertPostByPostForm(postDTO);

        if (!isInsertedTemp){
            responseWrapper.setErrorMessage("포스트 생성이 실패하였습니다.");
        }

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

        User userResult = resultSupporter.getUserResult(responseWrapper, username);
        if(userResult == null){
            return responseWrapper;
        }

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(responseWrapper, containerDTO);
        if(containerResult == null){
            return responseWrapper;
        }

        Optional<Post> optionalPost = postRepostiory.findPost(containerResult, postTitle);
        if (optionalPost.isEmpty()){
            responseWrapper.setErrorMessage("포스트 삭제가 실패하였습니다.");
            return responseWrapper;
        }

        boolean isDeletedTemp = postRepostiory.deletePost(optionalPost.get());

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

        User userResult = resultSupporter.getUserResult(responseWrapper, username);
        if(userResult == null){
            return responseWrapper;
        }

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(responseWrapper, containerDTO);
        if(containerResult == null){
            return responseWrapper;
        }

        Post postResult = resultSupporter.getPostResult(responseWrapper, containerResult, postTitle);
        if(postResult == null){
            return responseWrapper;
        }

        boolean isUpdatedTemp = postRepostiory.updatePost(postResult, postForm);

        responseWrapper.setObject(new Object(){
            public final boolean isUpdated = isUpdatedTemp;
        });
        return responseWrapper;
    }
}
