package api.jpa.practice.apiController;

import api.jpa.practice.domain.request.container.ContainerPathDTO;
import api.jpa.practice.domain.form.ContainerForm;
import api.jpa.practice.domain.request.PagingDTO;
import api.jpa.practice.domain.request.container.UpdateContainerDTO;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.service.ContainerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ContainerController {
    private final ContainerService containerService;

    @ApiOperation(value = "모든 컨테이너 조회", notes = "해당 유저의 모든 컨테이너 목록을 조회한다.")
    @GetMapping("/user/{username}/container-list")
    public ResponseWrapper getContainers(@PathVariable String username){
        return containerService.findContainersByUsername(username);
    }

    @ApiOperation(value = "일부 컨테이너 조회(페이징)", notes = "해당 유저의 컨테이너 목록 중 특정 범위를 조회한다.")
    @GetMapping("/user/{username}/container-list/{startPos}/{length}")
    public ResponseWrapper getContainersPaging(@PathVariable(name = "username") String username,
                                               @PathVariable(name = "startPos") int startPos,
                                               @PathVariable(name = "length") int length){
        return containerService.findContainersByUsername(username, new PagingDTO(startPos, length));
    }

    // containerTitle이 userId가 같다면 겹치지 않도록 설정
    @ApiOperation(value = "컨테이너 등록", notes = "컨테이너를 등록한다.")
    @PostMapping("/user/{username}/container")
    public ResponseWrapper createContainer(@PathVariable(name = "username") String username,
                                           @RequestBody @Validated ContainerForm containerForm){

        ContainerPathDTO containerPathDTO = new ContainerPathDTO();
        containerPathDTO.setUsername(username);
        containerPathDTO.setContainerTitle(containerForm.getContainerTitle());

        return containerService.createContainer(containerPathDTO);
    }

    // 검색
    @ApiOperation(value = "컨테이너 검색", notes = "해당 유저의 컨테이너에서 제목에 키워드가 들어간 컨테이너를 조회한다.")
    @GetMapping("/user/{username}/container-list/{containerKeyword}")
    public ResponseWrapper searchContainers(@PathVariable(name = "username") String username,
                                            @PathVariable(name = "containerKeyword") String containerKeyword){

        ContainerPathDTO containerPathDTO = new ContainerPathDTO();
        containerPathDTO.setUsername(username);
        containerPathDTO.setContainerTitle(containerKeyword);

        return containerService.searchContainers(containerPathDTO);
    }

    @ApiOperation(value = "컨테이너 검색 (paging)", notes = "해당 유저의 컨테이너에서 제목에 키워드가 들어간 컨테이너 중 특정 범위를 조회한다.")
    @GetMapping("/user/{username}/container-list/{containerKeyword}/{startPos}/{length}")
    public ResponseWrapper searchContainersPaging(@PathVariable(name = "username") String username,
                                                  @PathVariable(name = "containerKeyword") String containerKeyword,
                                                  @PathVariable(name = "startPos") int startPos,
                                                  @PathVariable(name = "length") int length){
        ContainerPathDTO containerPathDTO = new ContainerPathDTO();
        containerPathDTO.setUsername(username);
        containerPathDTO.setContainerTitle(containerKeyword);

        return containerService.searchContainers(containerPathDTO, new PagingDTO(startPos, length));
    }

    @ApiOperation(value = "컨테이너 조회", notes = "해당 컨테이너를 조회한다.")
    @GetMapping("/user/{username}/container/{containerTitle}")
    public ResponseWrapper findContainer(@PathVariable(name = "username") String username,
                                            @PathVariable(name = "containerTitle") String containerTitle){
        ContainerPathDTO containerPathDTO = new ContainerPathDTO();
        containerPathDTO.setUsername(username);
        containerPathDTO.setContainerTitle(containerTitle);

        return containerService.findContainer(containerPathDTO);
    }

    @ApiOperation(value = "컨테이너 삭제", notes = "해당 컨테이너를 삭제한다.")
    @DeleteMapping("/user/{username}/container/{containerTitle}")
    public ResponseWrapper deleteContainer(@PathVariable(name = "username") String username,
                                           @PathVariable(name = "containerTitle") String containerTitle){

        ContainerPathDTO containerPathDTO = new ContainerPathDTO();
        containerPathDTO.setUsername(username);
        containerPathDTO.setContainerTitle(containerTitle);

        return containerService.deleteContainer(containerPathDTO);
    }

    @ApiOperation(value = "컨테이너 수정", notes = "해당 컨테이너를 수정한다.")
    @PutMapping("/user/{username}/container/{containerTitle}")
    public ResponseWrapper updateContainer(@PathVariable(name = "username") String username,
                                           @PathVariable(name = "containerTitle") String containerTitle,
                                           @RequestBody @Validated ContainerForm containerForm){

        ContainerPathDTO containerPathDTO = new ContainerPathDTO();
        containerPathDTO.setUsername(username);
        containerPathDTO.setContainerTitle(containerTitle);

        UpdateContainerDTO updateContainerDTO = new UpdateContainerDTO();
        updateContainerDTO.setContainerPathDTO(containerPathDTO);
        updateContainerDTO.setContainerForm(containerForm);

        return containerService.updateContainer(updateContainerDTO);
    }
}
