package api.jpa.practice.apiController;

import api.jpa.practice.domain.form.RegisterForm;
import api.jpa.practice.domain.form.NewUserForm;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserContorller {
    private final UserService userService;

    @PostMapping("/user")
    public ResponseWrapper register(@RequestBody RegisterForm registerForm){
        return userService.insertUserByRegisterForm(registerForm);
    }

    @GetMapping("/user/{username}")
    public ResponseWrapper getUserInform(@PathVariable String username){
        return userService.getUserinform(username);
    }

    @DeleteMapping("/user/{username}")
    public ResponseWrapper deleteUser(@PathVariable String username){
        return userService.deleteUserByUsername(username);
    }
}
