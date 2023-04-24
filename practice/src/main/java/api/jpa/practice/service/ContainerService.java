package api.jpa.practice.service;

import api.jpa.practice.domain.form.ContainerForm;
import api.jpa.practice.domain.request.ContainerDTO;
import api.jpa.practice.domain.request.ContainerDTOWithUsername;
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

    @Transactional
    public ResponseWrapper createContainer(ContainerForm containerForm){
        String username = containerForm.getUsername();
        String title = containerForm.getContainerTitle();

        ResponseWrapper responseWrapper = new ResponseWrapper();

        User userResult = resultSupporter.getUserResult(responseWrapper, username);
        if (userResult == null){
            return responseWrapper;
        }

        Optional<Container> optionalContainer = containerRepostiory.findContainer(userResult, title);
        if (optionalContainer.isPresent()){
            responseWrapper.setErrorMessage("이미 존재하는 컨테이너 제목입니다.");
            return responseWrapper;
        }

        ContainerDTO containerDTO = new ContainerDTO(userResult, title);
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
    public ResponseWrapper findContainer(ContainerForm containerForm){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = containerForm.getUsername();
        String containerTitle = containerForm.getContainerTitle();

        User userResult = resultSupporter.getUserResult(responseWrapper, username);
        if (userResult == null){
            return responseWrapper;
        }

        Container containerResult = resultSupporter.getContainerResult(responseWrapper, userResult, containerTitle);
        if (containerResult == null){
            return responseWrapper;
        }

        responseWrapper.setObject(containerResult);
        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper searchContainers(ContainerForm containerForm){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        String username = containerForm.getUsername();
        String containerTitle = containerForm.getContainerTitle();

        User userResult = resultSupporter.getUserResult(responseWrapper, username);

        List<Container> containers = containerRepostiory.searchContainers(userResult, containerTitle);
        responseWrapper.setObject(containers);

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
        Long targetContainerId = updateContainerDTO.getTargetContainerId();

        User userResult = resultSupporter.getUserResult(responseWrapper, username);

        List<Container> containers = userResult.getContainers();

        if (containers.size() <= 0){
            responseWrapper.setErrorMessage("대상 유저가 컨테이너를 가지고 있지 않습니다.");
            return responseWrapper;
        }

        Optional<Container> optionalTargetContainer = containers.stream()
                .filter(c -> c.getContainerId().equals(targetContainerId))
                .findAny();

        if (optionalTargetContainer.isEmpty()){
            responseWrapper.setErrorMessage("유저에게서 대상 컨테이너를 찾을 수 없습니다.");
        }

        Container targetContainer = optionalTargetContainer.get();

        String tempOldTitle = targetContainer.getTitle();
        targetContainer.setTitle(updateContainerDTO.getSubmitContainerForm().getContainerTitle());

        responseWrapper.setObject(new Object(){
            public String oldTitle = tempOldTitle;
            public String updatedTitle = updateContainerDTO.getSubmitContainerForm().getContainerTitle();
        });

        return responseWrapper;
    }
}
