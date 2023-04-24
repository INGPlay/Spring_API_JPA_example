package api.jpa.practice.service;

import api.jpa.practice.domain.form.RegisterForm;
import api.jpa.practice.domain.request.RegisterDTO;
import api.jpa.practice.domain.response.RegisterResponse;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.domain.response.UserInformResponse;
import api.jpa.practice.entity.User;
import api.jpa.practice.entity.enums.UserRole;
import api.jpa.practice.repository.UserRepository;
import api.jpa.practice.service.component.ResultSupporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ResultSupporter resultSupporter;

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

        User userResult = resultSupporter.getUserResult(responseWrapper, username);
        if (userResult == null){
            return responseWrapper;
        }

        UserInformResponse userinformResponse = new UserInformResponse();
        userinformResponse.setUsername(userResult.getUsername());
        userinformResponse.setUserRole(userResult.getUserRole());
        userinformResponse.setTimeInform(userResult.getTimeInform());
        userinformResponse.setContainer(userResult.getContainers());

        responseWrapper.setObject(userinformResponse);
        return responseWrapper;
    }

    @Transactional
    public ResponseWrapper deleteUserByUsername(String username){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        User userResult = resultSupporter.getUserResult(responseWrapper, username);
        if (userResult == null){
            return responseWrapper;
        }

        boolean isDeletedTemp = userRepository.deleteUserByUser(userResult);

        responseWrapper.setObject(new Object(){
            public Boolean isDeleted = isDeletedTemp;
        });
        return responseWrapper;
    }

}
