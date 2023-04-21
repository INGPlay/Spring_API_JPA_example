package api.jpa.practice.service;

import api.jpa.practice.domain.form.RegisterForm;
import api.jpa.practice.domain.request.RegisterDTO;
import api.jpa.practice.domain.response.RegisterResponse;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.domain.response.UserInformResponse;
import api.jpa.practice.entity.User;
import api.jpa.practice.entity.enums.UserRole;
import api.jpa.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public ResponseWrapper insertUserByRegisterForm(RegisterForm registerForm){

        ResponseWrapper responseWrapper = new ResponseWrapper();
        RegisterResponse registerResponse = new RegisterResponse();
        // 중복 검사
        if (isDuplicatedUsername(registerForm.getUsername())){
            registerResponse.setRegister(false);
            registerResponse.setDuplicate(true);

            responseWrapper.setErrorMessage("이미 등록된 username 입니다.");
            responseWrapper.setObject(registerResponse);

            return responseWrapper;
        }

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername(registerForm.getUsername());
        registerDTO.setPassword(registerForm.getPassword());
        registerDTO.setUserRole(UserRole.NORMAL);

        boolean isRegistred = userRepository.insertUserByRegisterDTO(registerDTO);

        registerResponse.setRegister(isRegistred);
        responseWrapper.setObject(registerResponse);
        return responseWrapper;
    }

    public boolean isDuplicatedUsername(String username){
        User userByUsername = userRepository.findUserByUsername(username).orElseGet(()->{
            return null;
        });

        if (userByUsername == null){
            return false;
        }

        return true;
    }

    public ResponseWrapper getUserinform(String username){

        ResponseWrapper responseWrapper = new ResponseWrapper();

        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isEmpty()){
            responseWrapper.setErrorMessage("대상이 없습니다.");
            return responseWrapper;
        }

        User user = optionalUser.get();

        UserInformResponse userinformResponse = new UserInformResponse();
        userinformResponse.setUsername(user.getUsername());
        userinformResponse.setUserRole(user.getUserRole());
        userinformResponse.setTimeInform(user.getTimeInform());
        userinformResponse.setContainer(user.getContainers());

        responseWrapper.setObject(userinformResponse);
        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper deleteUserByUsername(String username){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isEmpty()){
            responseWrapper.setErrorMessage("대상이 없습니다.");
            return responseWrapper;
        }

        User user = optionalUser.get();

        boolean isDeleted = userRepository.deleteUserByUser(user);
        Map<String, Boolean> result = new HashMap<>();
        result.put("isDeleted", isDeleted);

        responseWrapper.setObject(result);
        return responseWrapper;
    }

}
