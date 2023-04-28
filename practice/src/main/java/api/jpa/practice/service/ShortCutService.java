package api.jpa.practice.service;

import api.jpa.practice.domain.request.container.ContainerDTO;
import api.jpa.practice.domain.request.PagingDTO;
import api.jpa.practice.domain.request.post.PostPathDTO;
import api.jpa.practice.domain.request.ShortCutDTO;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.exception.exceptions.conflict.ConflictShortCutException;
import api.jpa.practice.exception.exceptions.notFound.NotFoundShortCut;
import api.jpa.practice.entity.Container;
import api.jpa.practice.entity.Post;
import api.jpa.practice.entity.ShortCut;
import api.jpa.practice.entity.User;
import api.jpa.practice.repository.ShortCutRepository;
import api.jpa.practice.service.component.ResultSupporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShortCutService {
    private final ShortCutRepository shortCutRepository;
    private final ResultSupporter resultSupporter;

    @Transactional
    public ResponseWrapper linkShortCut(PostPathDTO postPathDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = postPathDTO.getUsername();
        String conatainerTitle = postPathDTO.getConatainerTitle();
        String postTitle = postPathDTO.getPostTitle();

        User userResult = resultSupporter.getUserResult(username);

        ContainerDTO containerDTO = new ContainerDTO();
        containerDTO.setUser(userResult);
        containerDTO.setContainerTitle(conatainerTitle);
        Container containerResult = resultSupporter.getContainerResult(containerDTO);

        Post postResult = resultSupporter.getPostResult(containerResult, postTitle);

        Optional<ShortCut> optionalShortCut = shortCutRepository.findShortCutWithUser(userResult, postResult.getPostId());
        if(optionalShortCut.isPresent()){
            throw new ConflictShortCutException();
        }

        ShortCutDTO shortCutDTO = new ShortCutDTO();
        shortCutDTO.setUser(userResult);
        shortCutDTO.setPost(postResult);

        boolean isLinkedTemp = shortCutRepository.insertShortCut(shortCutDTO);

        responseWrapper.setObject(new Object(){
            public final boolean isLinked = isLinkedTemp;
        });

        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper unlinkShortCut(String username, Long shortCutId){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        User userResult = resultSupporter.getUserResult(username);

        Optional<ShortCut> optionalShortCut = shortCutRepository.findShortCutWithUser(userResult, shortCutId);
        if (optionalShortCut.isEmpty()){
            throw new NotFoundShortCut();
        }
        boolean isDeletedTemp = shortCutRepository.deleteShortCut(optionalShortCut.get());

        responseWrapper.setObject(new Object(){
            public final boolean isDeleted = isDeletedTemp;
        });

        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper findShortCutsByUsername(String username){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        List<ShortCut> shortCuts = shortCutRepository.findShortCuts(username);
        responseWrapper.setObject(shortCuts);

        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper findShortCutsByUsername(String username, PagingDTO pagingDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        List<ShortCut> shortCuts = shortCutRepository.findShortCuts(username, pagingDTO);
        responseWrapper.setObject(shortCuts);

        return responseWrapper;
    }
}
