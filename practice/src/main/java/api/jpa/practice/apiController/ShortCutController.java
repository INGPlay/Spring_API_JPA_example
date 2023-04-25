package api.jpa.practice.apiController;

import api.jpa.practice.domain.form.InputIdForm;
import api.jpa.practice.domain.request.PostPathDTO;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.service.ShortCutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ShortCutController {
    private final ShortCutService shortCutService;

    @GetMapping("/user/{username}/shortcut-list")
    public ResponseWrapper findShortCuts(@PathVariable String username){
        return shortCutService.findShortCutsByUsername(username);
    }

    @DeleteMapping("/user/{username}/shortcut")
    public ResponseWrapper unlinkShortCut(@PathVariable String username,
                                          @RequestBody InputIdForm inputIdForm){
        return shortCutService.unlinkShortCut(username, inputIdForm.getShortCutId());
    }

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
