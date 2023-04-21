package api.jpa.practice.apiController;

import api.jpa.practice.domain.form.ContainerForm;
import api.jpa.practice.domain.form.UpdateContainerForm;
import api.jpa.practice.domain.request.ContainerDTO;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.service.ContainerService;
import api.jpa.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ContainerController {
    private final UserService userService;
    private final ContainerService containerService;

    @GetMapping("/user/{username}/container")
    public ResponseWrapper getContainers(@PathVariable String username){
        return containerService.findContainersByUsername(username);
    }

    @PostMapping("/user/{username}/container")
    public ResponseWrapper createContainer(@PathVariable String username,
                                           @RequestBody String containerTitle){
        ContainerForm containerForm = new ContainerForm();
        containerForm.setUsername(username);
        containerForm.setContainerTitle(containerTitle);

        return containerService.createContainer(containerForm);
    }

    @GetMapping("/user/{username}/container/{containerTitle}")
    public ResponseWrapper getContainer(@PathVariable ContainerForm containerForm){
        return new ResponseWrapper();
    }

    @DeleteMapping("/user/{username}/container/{containerTitle}")
    public ResponseWrapper deleteContainer(@PathVariable ContainerForm containerForm){
        return new ResponseWrapper();
    }

    @PutMapping("/user/{username}/container/{containerTitle}")
    public ResponseWrapper updateContainer(@RequestBody UpdateContainerForm updateContainerForm){
        return new ResponseWrapper();
    }
}
