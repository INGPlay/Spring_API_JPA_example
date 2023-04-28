package api.jpa.practice.apiController;

import api.jpa.practice.domain.form.UserForm;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserContorller {
    private final UserService userService;

    @ApiOperation(value = "유저 등록", notes = "해당 유저를 데이터베이스에 등록한다.")
    @PostMapping("/user")
    public ResponseWrapper register(@RequestBody @Validated UserForm userForm){
        return userService.insertUser(userForm);
    }

    @ApiOperation(value = "유저 선택", notes = "해당 유저의 정보를 불러온다.")
    @GetMapping("/user/{username}")
    public ResponseWrapper getUserInform(@PathVariable String username){
        return userService.getUserinform(username);
    }

    @ApiOperation(value = "유저 삭제", notes = "해당 유저를 데이터베이스에서 삭제한다.")
    @DeleteMapping("/user/{username}")
    public ResponseWrapper deleteUser(@PathVariable String username){
        return userService.deleteUserByUsername(username);
    }
}
