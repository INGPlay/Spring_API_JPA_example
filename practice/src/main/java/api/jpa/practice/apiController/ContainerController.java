package api.jpa.practice.apiController;

import api.jpa.practice.domain.form.ContainerForm;
import api.jpa.practice.domain.form.SubmitContainerForm;
import api.jpa.practice.domain.request.UpdateContainerDTO;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.entity.Container;
import api.jpa.practice.service.ContainerService;
import api.jpa.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ContainerController {
    private final ContainerService containerService;

    @GetMapping("/user/{username}/container-list")
    public ResponseWrapper getContainers(@PathVariable String username){
        return containerService.findContainersByUsername(username);
    }

    // containerTitle이 userId가 같다면 겹치지 않도록 설정
    @PostMapping("/user/{username}/container")
    public ResponseWrapper createContainer(@PathVariable(name = "username") String username,
                                           @RequestBody SubmitContainerForm submitContainerForm){

        ContainerForm containerForm = new ContainerForm();
        containerForm.setUsername(username);
        containerForm.setContainerTitle(submitContainerForm.getContainerTitle());

        return containerService.createContainer(containerForm);
    }

    @GetMapping("/user/{username}/container-list/{containerTitle}")
    public ResponseWrapper searchContainers(@PathVariable(name = "username") String username,
                                            @PathVariable(name = "containerTitle") String containerTitle){
        ContainerForm containerForm = new ContainerForm();
        containerForm.setUsername(username);
        containerForm.setContainerTitle(containerTitle);

        return containerService.searchContainers(containerForm);
    }

    @GetMapping("/user/{username}/container/{containerTitle}")
    public ResponseWrapper findContainer(@PathVariable(name = "username") String username,
                                            @PathVariable(name = "containerTitle") String containerTitle){
        ContainerForm containerForm = new ContainerForm();
        containerForm.setUsername(username);
        containerForm.setContainerTitle(containerTitle);

        return containerService.findContainer(containerForm);
    }

    @DeleteMapping("/user/{username}/container/{containerId}")
    public ResponseWrapper deleteContainer(@PathVariable Long containerId){

        return containerService.deleteContainerByContainerId(containerId);
    }

    @PutMapping("/user/{username}/container/{containerId}")
    public ResponseWrapper updateContainer(@PathVariable(name = "username") String username,
                                           @PathVariable(name = "containerId") Long containerId,
                                           @RequestBody SubmitContainerForm submitContainerForm){

        UpdateContainerDTO updateContainerDTO = new UpdateContainerDTO();
        updateContainerDTO.setUsername(username);
        updateContainerDTO.setTargetContainerId(containerId);
        updateContainerDTO.setSubmitContainerForm(submitContainerForm);

        return containerService.updateContainer(updateContainerDTO);
    }
}
