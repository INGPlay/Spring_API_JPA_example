package api.jpa.practice.service;

import api.jpa.practice.domain.request.ContainerPathDTO;
import api.jpa.practice.domain.form.ContainerForm;
import api.jpa.practice.domain.request.ContainerDTO;
import api.jpa.practice.domain.request.PagingDTO;
import api.jpa.practice.domain.request.UpdateContainerDTO;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.entity.Container;
import api.jpa.practice.entity.User;
import api.jpa.practice.repository.ContainerRepostiory;
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
public class ContainerService {
    private final ContainerRepostiory containerRepostiory;
    private final UserRepository userRepository;
    private final ResultSupporter resultSupporter;

    public ResponseWrapper findContainersByUsername(String username) {
        ResponseWrapper responseWrapper = new ResponseWrapper();

        User userResult = resultSupporter.getUserResult(responseWrapper, username);
        if (userResult == null){
            return responseWrapper;
        }

        List<Container> containers = containerRepostiory.findContainersByUser(userResult);
        responseWrapper.setObject(containers);

        return responseWrapper;
    }
    // 오버로딩
    public ResponseWrapper findContainersByUsername(String username, PagingDTO pagingDTO) {
        ResponseWrapper responseWrapper = new ResponseWrapper();

        User userResult = resultSupporter.getUserResult(responseWrapper, username);
        if (userResult == null){
            return responseWrapper;
        }

        List<Container> containers = containerRepostiory.findContainersByUser(userResult, pagingDTO);
        responseWrapper.setObject(containers);

        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper createContainer(ContainerPathDTO containerPathDTO){
        String username = containerPathDTO.getUsername();
        String containerTitle = containerPathDTO.getContainerTitle();

        ResponseWrapper responseWrapper = new ResponseWrapper();

        User userResult = resultSupporter.getUserResult(responseWrapper, username);
        if (userResult == null){
            return responseWrapper;
        }

        ContainerDTO containerDTO = new ContainerDTO();
        containerDTO.setUser(userResult);
        containerDTO.setContainerTitle(containerTitle);
        Optional<Container> optionalContainer = containerRepostiory.findContainer(containerDTO);
        if (optionalContainer.isPresent()){
            responseWrapper.setErrorMessage("이미 존재하는 컨테이너 제목입니다.");
            return responseWrapper;
        }

        boolean isInsertedTemp = containerRepostiory.insertContainer(containerDTO);

        if(!isInsertedTemp){
            responseWrapper.setErrorMessage("생성이 실패하였습니다.");
        }

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

        User userResult = resultSupporter.getUserResult(responseWrapper, username);
        if (userResult == null){
            return responseWrapper;
        }

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(responseWrapper, containerDTO);
        if (containerResult == null){
            return responseWrapper;
        }

        responseWrapper.setObject(containerResult);
        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper searchContainers(ContainerPathDTO containerPathDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = containerPathDTO.getUsername();
        String containerTitle = containerPathDTO.getContainerTitle();

        User userResult = resultSupporter.getUserResult(responseWrapper, username);

        List<Container> containers = containerRepostiory.searchContainers(userResult, containerTitle);
        responseWrapper.setObject(containers);

        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper searchContainers(ContainerPathDTO containerPathDTO, PagingDTO pagingDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = containerPathDTO.getUsername();
        String containerTitle = containerPathDTO.getContainerTitle();

        User userResult = resultSupporter.getUserResult(responseWrapper, username);

        List<Container> containers = containerRepostiory.searchContainers(userResult, containerTitle, pagingDTO);
        responseWrapper.setObject(containers);

        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper deleteContainer(ContainerPathDTO containerPathDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        String username = containerPathDTO.getUsername();
        String containerTitle = containerPathDTO.getContainerTitle();

        User userResult = resultSupporter.getUserResult(responseWrapper, username);
        if (userResult == null){
            return responseWrapper;
        }

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(responseWrapper, containerDTO);
        if (containerResult == null){
            return responseWrapper;
        }

        boolean isDeletedTemp = containerRepostiory.deleteContainer(containerResult);

        responseWrapper.setObject(new Object(){
            public final boolean isDeleted = isDeletedTemp;
        });

        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper deleteContainerByContainerId(Long containerId){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        Optional<Container> optionalContainer = containerRepostiory.findContainerById(containerId);

        if (optionalContainer.isEmpty()){
            responseWrapper.setErrorMessage("삭제할 대상이 없습니다.");
            return responseWrapper;
        }

        boolean result = containerRepostiory.deleteContainer(optionalContainer.get());

        Map<String, Boolean> resultMap = new HashMap<>();
        resultMap.put("isDeleted", result);
        responseWrapper.setObject(resultMap);

        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper updateContainer(UpdateContainerDTO updateContainerDTO){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = updateContainerDTO.getUsername();
        String containerTitle = updateContainerDTO.getContainerTitle();
        ContainerForm containerForm = updateContainerDTO.getContainerForm();

        User userResult = resultSupporter.getUserResult(responseWrapper, username);
        if (userResult == null){
            return responseWrapper;
        }

        ContainerDTO containerDTO = new ContainerDTO(userResult, containerTitle);
        Container containerResult = resultSupporter.getContainerResult(responseWrapper, containerDTO);
        if (containerResult == null){
            return responseWrapper;
        }

        boolean isUpdatedTemp = containerRepostiory.updateContainer(containerResult, containerForm);

        responseWrapper.setObject(new Object(){
            public final boolean isUpdated = isUpdatedTemp;
        });

        return responseWrapper;
    }
}
