package api.jpa.practice.apiController;

import api.jpa.practice.domain.form.InputIdForm;
import api.jpa.practice.domain.request.PagingDTO;
import api.jpa.practice.domain.request.post.PostPathDTO;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.service.ShortCutService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ShortCutController {
    private final ShortCutService shortCutService;

    @ApiOperation(value = "모든 즐겨찾기 포스트 조회", notes = "해당 유저의 모든 즐겨찾기한 포스트 목록을 조회한다.")
    @GetMapping("/user/{username}/shortcut-list")
    public ResponseWrapper findShortCuts(@PathVariable String username){
        return shortCutService.findShortCutsByUsername(username);
    }

    @ApiOperation(value = "일부 즐겨찾기 포스트 조회(페이징)", notes = "해당 유저의 즐겨찾기한 포스트 목록 중 특정 범위를 조회한다.")
    @GetMapping("/user/{username}/shortcut-list/{startPos}/{length}")
    public ResponseWrapper findShortCutsPaging(@PathVariable(name = "username") String username,
                                               @PathVariable(name = "startPos") int startPos,
                                               @PathVariable(name = "length") int length){
        return shortCutService.findShortCutsByUsername(username, new PagingDTO(startPos, length));
    }

    @ApiOperation(value = "즐겨찾기 삭제", notes = "해당 포스트를 즐겨찾기 목록에서 삭제한다.")
    @DeleteMapping("/user/{username}/shortcut")
    public ResponseWrapper unlinkShortCut(@PathVariable String username,
                                          @RequestBody InputIdForm inputIdForm){
        return shortCutService.unlinkShortCut(username, inputIdForm.getShortCutId());
    }

    @ApiOperation(value = "즐겨찾기 추가", notes = "해당 포스트를 즐겨찾기 목록에서 추가한다.")
    @PostMapping("/user/{username}/container/{containerTitle}/post/{postTitle}/shortcut")
    public ResponseWrapper linkShortCut(@PathVariable(name = "username") String username,
                                        @PathVariable(name = "containerTitle") String containerTitle,
                                        @PathVariable(name = "postTitle") String postTitle){

        PostPathDTO postPathDTO = new PostPathDTO();
        postPathDTO.setUsername(username);
        postPathDTO.setConatainerTitle(containerTitle);
        postPathDTO.setPostTitle(postTitle);

        return shortCutService.linkShortCut(postPathDTO);
    }
}
