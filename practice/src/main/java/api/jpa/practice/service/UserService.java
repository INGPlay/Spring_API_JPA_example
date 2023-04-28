package api.jpa.practice.service;

import api.jpa.practice.domain.form.UserForm;
import api.jpa.practice.domain.request.RegisterDTO;
import api.jpa.practice.domain.response.ResponseWrapper;
import api.jpa.practice.domain.response.UserInformResponse;
import api.jpa.practice.exception.exceptions.conflict.ConflictUserException;
import api.jpa.practice.entity.User;
import api.jpa.practice.entity.enums.UserRole;
import api.jpa.practice.repository.UserRepository;
import api.jpa.practice.service.component.ResultSupporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ResultSupporter resultSupporter;

    @Transactional
    public ResponseWrapper insertUser(UserForm userForm){

        ResponseWrapper responseWrapper = new ResponseWrapper();
        // 중복 검사
        if (isDuplicatedUsername(userForm.getUsername())){
            throw new ConflictUserException();
        }

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername(userForm.getUsername());
        registerDTO.setPassword(userForm.getPassword());
        registerDTO.setUserRole(UserRole.NORMAL);

        boolean isRegister = userRepository.insertUserByRegisterDTO(registerDTO);

        responseWrapper.setObject(new HashMap<String, Boolean>() {
            {
                put("isRegister", isRegister);
            }
        });
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

        User userResult = resultSupporter.getUserResult(username);

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

        User userResult = resultSupporter.getUserResult(username);
        if (userResult == null){
            return responseWrapper;
        }

        boolean isDeleted = userRepository.deleteUserByUser(userResult);

        responseWrapper.setObject(new HashMap<String, Boolean>(){
            {
                put("isDeleted", isDeleted);
            }
        });
        return responseWrapper;
    }

}
