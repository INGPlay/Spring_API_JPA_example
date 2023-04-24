package api.jpa.practice.service;

import api.jpa.practice.domain.form.ContainerForm;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepostiory postRepostiory;
    private final ContainerRepostiory containerRepostiory;
    private final ResultSupporter resultSupporter;

    public ResponseWrapper findPosts(ContainerForm containerForm){

        ResponseWrapper responseWrapper = new ResponseWrapper();

        User userResult = resultSupporter.getUserResult(responseWrapper, containerForm.getUsername());
        if (userResult == null){
            return responseWrapper;
        }

        List<Container> containers = containerRepostiory.findContainersByUser(userResult);

        if (containers.size() <= 0){
            responseWrapper.setErrorMessage("대상 유저가 컨테이너를 가지고 있지 않습니다.");
            return responseWrapper;
        }

        List<Post> posts = postRepostiory.findPostsByContainerForm(containerForm);

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

        Container containerResult = resultSupporter.getContainerResult(responseWrapper, userResult, containerTitle);
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

        Container containerResult = resultSupporter.getContainerResult(responseWrapper, userResult, containerTitle);
        if(containerResult == null){
            return responseWrapper;
        }

        List<Post> posts = postRepostiory.searchPosts(containerResult, postTitle);
        if (posts.size() <= 0){
            responseWrapper.setErrorMessage("검색된 포스트가 없습니다.");
            return responseWrapper;
        }

        responseWrapper.setObject(posts);
        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper createPost(CreatePostDTO createPostDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = createPostDTO.getUsername();
        String containerTitle = createPostDTO.getContainerTitle();
        PostForm postForm = createPostDTO.getPostForm();

        Optional<User> optionalUser = userRepository.findUserByUsername(username);

        if (optionalUser.isEmpty()){
            responseWrapper.setErrorMessage("유저가 존재하지 않습니다.");
            return responseWrapper;
        }

        ContainerDTO containerDTO = new ContainerDTO();
        containerDTO.setUser(optionalUser.get());
        containerDTO.setContainerTitle(containerTitle);
        Optional<Container> optionalContainer = containerRepostiory.findContainer(containerDTO);

        if (optionalContainer.isEmpty()){
            responseWrapper.setErrorMessage("컨테이너가 없습니다.");
            return responseWrapper;
        }

        Container container = optionalContainer.get();

        Optional<Post> optionalPost = postRepostiory.findPost(container, postForm.getPostTitle());

        if (optionalPost.isPresent()){
            responseWrapper.setErrorMessage("같은 제목의 포스트가 이미 존재합니다.");
            return responseWrapper;
        }

        PostDTO postDTO = new PostDTO();
        postDTO.setContainer(container);
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


}
