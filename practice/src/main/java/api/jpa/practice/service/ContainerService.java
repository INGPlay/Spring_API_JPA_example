package api.jpa.practice.service;

import api.jpa.practice.domain.request.container.ContainerPathDTO;
import api.jpa.practice.domain.form.ContainerForm;
import api.jpa.practice.domain.request.container.ContainerDTO;
import api.jpa.practice.domain.request.PagingDTO;
import api.jpa.practice.domain.request.container.UpdateContainerDTO;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.exception.exceptions.conflict.ConflictContainerException;
import api.jpa.practice.entity.Container;
import api.jpa.practice.entity.User;
import api.jpa.practice.repository.ContainerRepostiory;
import api.jpa.practice.repository.UserRepository;
import api.jpa.practice.service.component.ResultSupporter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContainerService {
    private final ContainerRepostiory containerRepostiory;
    private final UserRepository userRepository;
    private final ResultSupporter resultSupporter;

    public ResponseWrapper findContainersByUsername(String username) {
        ResponseWrapper responseWrapper = new ResponseWrapper();

        User userResult = resultSupporter.getUserResult(username);

        List<Container> containers = containerRepostiory.findContainersByUser(userResult);
        responseWrapper.setObject(containers);

        return responseWrapper;
    }
    // 오버로딩
    public ResponseWrapper findContainersByUsername(String username, PagingDTO pagingDTO) {
        ResponseWrapper responseWrapper = new ResponseWrapper();

        User userResult = resultSupporter.getUserResult(username);

        List<Container> containers = containerRepostiory.findContainersByUser(userResult, pagingDTO);
        responseWrapper.setObject(containers);

        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper createContainer(ContainerPathDTO containerPathDTO){
        String username = containerPathDTO.getUsername();
        String containerTitle = containerPathDTO.getContainerTitle();

        ResponseWrapper responseWrapper = new ResponseWrapper();

        User userResult = resultSupporter.getUserResult(username);

        ContainerDTO containerDTO = new ContainerDTO();
        containerDTO.setUser(userResult);
        containerDTO.setContainerTitle(containerTitle);
        Optional<Container> optionalContainer = containerRepostiory.findContainer(containerDTO);
        if (optionalContainer.isPresent()){
//            responseWrapper.setErrorMessage("이미 존재하는 컨테이너 제목입니다.");
//            return responseWrapper;
            throw new ConflictContainerException();
        }

        boolean isInsertedTemp = containerRepostiory.insertContainer(containerDTO);

        responseWrapper.setObject(new Object(){
            public final boolean isCreated = isInsertedTemp;
        });
        return responseWrapper;

    }

    @Transactional
    public ResponseWrapper findContainer(ContainerPathDTO containerPathDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = containerPathDTO.getUsername();
        String containerTitle = containerPathDTO.getContainerTitle();

        User userResult = resultSupporter.getUserResult(username);

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(containerDTO);

        responseWrapper.setObject(containerResult);
        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper searchContainers(ContainerPathDTO containerPathDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = containerPathDTO.getUsername();
        String containerTitle = containerPathDTO.getContainerTitle();

        User userResult = resultSupporter.getUserResult(username);

        log.info(containerTitle);
        List<Container> containers = containerRepostiory.searchContainers(userResult, containerTitle);
        responseWrapper.setObject(containers);

        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper searchContainers(ContainerPathDTO containerPathDTO, PagingDTO pagingDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = containerPathDTO.getUsername();
        String containerTitle = containerPathDTO.getContainerTitle();

        User userResult = resultSupporter.getUserResult(username);

        ContainerDTO containerDTO = new ContainerDTO();
        containerDTO.setUser(userResult);
        containerDTO.setContainerTitle(containerTitle);


        List<Container> containers = containerRepostiory.searchContainers(containerDTO, pagingDTO);
        responseWrapper.setObject(containers);

        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper deleteContainer(ContainerPathDTO containerPathDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        String username = containerPathDTO.getUsername();
        String containerTitle = containerPathDTO.getContainerTitle();

        User userResult = resultSupporter.getUserResult(username);

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(containerDTO);

        boolean isDeletedTemp = containerRepostiory.deleteContainer(containerResult);

        responseWrapper.setObject(new Object(){
            public final boolean isDeleted = isDeletedTemp;
        });

        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper updateContainer(UpdateContainerDTO updateContainerDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = updateContainerDTO.getContainerPathDTO().getUsername();
        String containerTitle = updateContainerDTO.getContainerPathDTO().getContainerTitle();
        ContainerForm containerForm = updateContainerDTO.getContainerForm();

        User userResult = resultSupporter.getUserResult(username);

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(containerDTO);

        boolean isUpdatedTemp = containerRepostiory.updateContainer(containerResult, containerForm);

        responseWrapper.setObject(new Object(){
            public final boolean isUpdated = isUpdatedTemp;
        });

        return responseWrapper;
    }
}
