package api.jpa.practice.service;

import api.jpa.practice.domain.form.ContainerForm;
import api.jpa.practice.domain.request.ContainerDTO;
import api.jpa.practice.domain.request.ContainerDTOWithUserId;
import api.jpa.practice.domain.request.ContainerDTOWithUsername;
import api.jpa.practice.domain.request.UpdateContainerDTO;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.entity.Container;
import api.jpa.practice.entity.User;
import api.jpa.practice.repository.ContainerRepostiory;
import api.jpa.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContainerService {
    private final ContainerRepostiory containerRepostiory;
    private final UserRepository userRepository;

    public ResponseWrapper findContainersByUsername(String username) {
        ResponseWrapper responseWrapper = new ResponseWrapper();

        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isEmpty()){
            responseWrapper.setErrorMessage("대상이 없습니다.");
            return responseWrapper;
        }

        List<Container> containers = containerRepostiory.findContainersByUser(optionalUser.get());
        responseWrapper.setObject(containers);

        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper createContainer(ContainerForm containerForm){
        String username = containerForm.getUsername();

        ResponseWrapper responseWrapper = new ResponseWrapper();

        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isEmpty()){
            responseWrapper.setErrorMessage("대상이 없습니다.");
            return responseWrapper;
        }

        ContainerDTOWithUsername containerDTO = new ContainerDTOWithUsername(username, containerForm.getContainerTitle());
        boolean isInserted = containerRepostiory.insertContainerWithUsername(containerDTO);

        Map<String, Boolean> inserted = new HashMap<>();
        inserted.put("isInserted", isInserted);
        responseWrapper.setObject(inserted);

        if(!isInserted){
            responseWrapper.setErrorMessage("생성이 실패하였습니다.");
        }

        return responseWrapper;

    }

    @Transactional
    public ResponseWrapper searchContainers(ContainerForm containerForm){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        Optional<User> optionalUser = userRepository.findUserByUsername(containerForm.getUsername());
        if (optionalUser.isEmpty()){
            responseWrapper.setErrorMessage("대상이 없습니다.");
            return responseWrapper;
        }

        List<Container> containers = containerRepostiory.searchContainers(optionalUser.get(), containerForm.getContainerTitle());
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

        Optional<User> optionalUser = userRepository.findUserByUsername(updateContainerDTO.getUsername());
        if (optionalUser.isEmpty()){
            responseWrapper.setErrorMessage("대상 유저가 없습니다.");
            return responseWrapper;
        }

        List<Container> containers = optionalUser.get().getContainers();

        if (containers.size() <= 0){
            responseWrapper.setErrorMessage("대상 유저가 컨테이너를 가지고 있지 않습니다.");
            return responseWrapper;
        }

        Optional<Container> optionalTargetContainer = containers.stream()
                .filter(c -> c.getContainerId().equals(updateContainerDTO.getTargetContainerId()))
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
