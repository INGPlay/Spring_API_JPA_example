package api.jpa.practice.apiController;

import api.jpa.practice.domain.request.ContainerPathDTO;
import api.jpa.practice.domain.form.ContainerForm;
import api.jpa.practice.domain.request.PagingDTO;
import api.jpa.practice.domain.request.UpdateContainerDTO;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.service.ContainerService;
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

    @GetMapping("/user/{username}/container-list/{startPos}/{length}")
    public ResponseWrapper getContainersPaging(@PathVariable(name = "username") String username,
                                               @PathVariable(name = "startPos") int startPos,
                                               @PathVariable(name = "length") int length){
        return containerService.findContainersByUsername(username, new PagingDTO(startPos, length));
    }

    // containerTitle이 userId가 같다면 겹치지 않도록 설정
    @PostMapping("/user/{username}/container")
    public ResponseWrapper createContainer(@PathVariable(name = "username") String username,
                                           @RequestBody ContainerForm containerForm){

        ContainerPathDTO containerPathDTO = new ContainerPathDTO();
        containerPathDTO.setUsername(username);
        containerPathDTO.setContainerTitle(containerForm.getContainerTitle());

        return containerService.createContainer(containerPathDTO);
    }

    // 검색
    @GetMapping("/user/{username}/container-list/{containerTitle}")
    public ResponseWrapper searchContainers(@PathVariable(name = "username") String username,
                                            @PathVariable(name = "containerTitle") String containerTitle){
        ContainerPathDTO containerPathDTO = new ContainerPathDTO();
        containerPathDTO.setUsername(username);
        containerPathDTO.setContainerTitle(containerTitle);

        return containerService.searchContainers(containerPathDTO);
    }

    @GetMapping("/user/{username}/container-list/{containerTitle}/{startPos}/{length}")
    public ResponseWrapper searchContainersPaging(@PathVariable(name = "username") String username,
                                                  @PathVariable(name = "containerTitle") String containerTitle,
                                                  @PathVariable(name = "startPos") int startPos,
                                                  @PathVariable(name = "length") int length){
        ContainerPathDTO containerPathDTO = new ContainerPathDTO();
        containerPathDTO.setUsername(username);
        containerPathDTO.setContainerTitle(containerTitle);

        return containerService.searchContainers(containerPathDTO, new PagingDTO(startPos, length));
    }

    @GetMapping("/user/{username}/container/{containerTitle}")
    public ResponseWrapper findContainer(@PathVariable(name = "username") String username,
                                            @PathVariable(name = "containerTitle") String containerTitle){
        ContainerPathDTO containerPathDTO = new ContainerPathDTO();
        containerPathDTO.setUsername(username);
        containerPathDTO.setContainerTitle(containerTitle);

        return containerService.findContainer(containerPathDTO);
    }

    @DeleteMapping("/user/{username}/container/{containerTitle}")
    public ResponseWrapper deleteContainer(@PathVariable(name = "username") String username,
                                           @PathVariable(name = "containerTitle") String containerTitle){

        ContainerPathDTO containerPathDTO = new ContainerPathDTO();
        containerPathDTO.setUsername(username);
        containerPathDTO.setContainerTitle(containerTitle);

        return containerService.deleteContainer(containerPathDTO);
    }

    @PutMapping("/user/{username}/container/{containerTitle}")
    public ResponseWrapper updateContainer(@PathVariable(name = "username") String username,
                                           @PathVariable(name = "containerTitle") String containerTitle,
                                           @RequestBody ContainerForm containerForm){

        UpdateContainerDTO updateContainerDTO = new UpdateContainerDTO();
        updateContainerDTO.setUsername(username);
        updateContainerDTO.setContainerTitle(containerTitle);
        updateContainerDTO.setContainerForm(containerForm);

        return containerService.updateContainer(updateContainerDTO);
    }
}
