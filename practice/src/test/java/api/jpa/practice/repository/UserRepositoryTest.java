package api.jpa.practice.repository;

import api.jpa.practice.domain.request.RegisterForm;
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
        RegisterForm registerForm = new RegisterForm();
        registerForm.setUsername("faraway");
        registerForm.setPassword("origin");
        registerForm.setUserRole(UserRole.ADMIN);

        boolean isInserted = userRepository.insertUserByRegisterForm(registerForm);

        RegisterForm registerForm2 = new RegisterForm();
        registerForm2.setUsername("faraway2");
        registerForm2.setPassword("admin");
        registerForm2.setUserRole(UserRole.ADMIN);
        boolean isInserted2 = userRepository.insertUserByRegisterForm(registerForm2);
    }

    @Test
    @Rollback(value = false)
    void testInsert(){
        RegisterForm registerForm = new RegisterForm();
        registerForm.setUsername("asdf1");
        registerForm.setPassword("normal");
        registerForm.setUserRole(UserRole.NORMAL);

        boolean isInserted = userRepository.insertUserByRegisterForm(registerForm);

        RegisterForm registerForm2 = new RegisterForm();
        registerForm2.setUsername("ffff");
        registerForm2.setPassword("normal");
        registerForm2.setUserRole(UserRole.ADMIN);
        boolean isInserted2 = userRepository.insertUserByRegisterForm(registerForm2);

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