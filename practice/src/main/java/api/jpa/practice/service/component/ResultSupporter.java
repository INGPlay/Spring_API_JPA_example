package api.jpa.practice.service.component;

import api.jpa.practice.domain.request.container.ContainerDTO;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.domain.response.exception.exceptions.notFound.NotFoundContainerException;
import api.jpa.practice.domain.response.exception.exceptions.notFound.NotFoundPostException;
import api.jpa.practice.domain.response.exception.exceptions.notFound.NotFoundUserException;
import api.jpa.practice.entity.Container;
import api.jpa.practice.entity.Post;
import api.jpa.practice.entity.User;
import api.jpa.practice.repository.ContainerRepostiory;
import api.jpa.practice.repository.PostRepostiory;
import api.jpa.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResultSupporter {
    private final UserRepository userRepository;
    private final ContainerRepostiory containerRepostiory;
    private final PostRepostiory postRepostiory;

    // 지역함수
    public User getUserResult(String username){

        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isEmpty()){
//            responseWrapper.setErrorMessage("유저가 존재하지 않습니다.");
//            return null;
            throw new NotFoundUserException();
        }
        // 오류가 없다면.
        return optionalUser.get();
    }

    public Container getContainerResult(ContainerDTO containerDTO){

        Optional<Container> optionalContainer = containerRepostiory.findContainer(containerDTO);
        if (optionalContainer.isEmpty()){
//            responseWrapper.setErrorMessage("컨테이너가 존재하지 않습니다.");
//            return null;
            throw new NotFoundContainerException();
        }
        // 오류가 없다면.
        return optionalContainer.get();
    }

    public Post getPostResult(Container container, String postTitle){

        Optional<Post> optionalPost = postRepostiory.findPost(container, postTitle);
        if (optionalPost.isEmpty()){
//            responseWrapper.setErrorMessage("포스트가 존재하지 않습니다.");
//            return null;
            throw new NotFoundPostException();
        }
        // 오류가 없다면.
        return optionalPost.get();
    }
}
