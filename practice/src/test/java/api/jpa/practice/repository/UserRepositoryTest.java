package api.jpa.practice.repository;

import api.jpa.practice.domain.request.RegisterDTO;
import api.jpa.practice.entity.User;
import api.jpa.practice.entity.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@Slf4j
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach(){
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("faraway");
        registerDTO.setPassword("origin");
        registerDTO.setUserRole(UserRole.ADMIN);

        boolean isInserted = userRepository.insertUserByRegisterDTO(registerDTO);

        RegisterDTO registerDTO2 = new RegisterDTO();
        registerDTO2.setUsername("faraway2");
        registerDTO2.setPassword("admin");
        registerDTO2.setUserRole(UserRole.ADMIN);
        boolean isInserted2 = userRepository.insertUserByRegisterDTO(registerDTO2);
    }

    @Test
    @Rollback(value = false)
    void testInsert(){
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("asdf1");
        registerDTO.setPassword("normal");
        registerDTO.setUserRole(UserRole.NORMAL);

        boolean isInserted = userRepository.insertUserByRegisterDTO(registerDTO);

        RegisterDTO registerDTO2 = new RegisterDTO();
        registerDTO2.setUsername("ffff");
        registerDTO2.setPassword("normal");
        registerDTO2.setUserRole(UserRole.ADMIN);
        boolean isInserted2 = userRepository.insertUserByRegisterDTO(registerDTO2);

        log.info("isInserted : {}", isInserted);
        log.info("isInserted2 : {}", isInserted2);
    }

    @Test
    void findTest(){
        User user = userRepository.findUserByUsername("faraway").get();
        String username = user.getUsername();
        String password = user.getPassword();
        UserRole userRole = user.getUserRole();

        assertThat(username).isEqualTo("faraway");
        assertThat(password).isEqualTo("origin");
        assertThat(userRole).isEqualTo(UserRole.ADMIN);
    }
}