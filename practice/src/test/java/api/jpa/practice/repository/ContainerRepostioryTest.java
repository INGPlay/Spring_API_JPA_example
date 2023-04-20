package api.jpa.practice.repository;

import api.jpa.practice.domain.request.ContainerForm;
import api.jpa.practice.domain.request.RegisterForm;
import api.jpa.practice.entity.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@Slf4j
class ContainerRepostioryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContainerRepostiory containerRepostiory;

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

        log.info("isInserted : {}, isInserted2 : {}", isInserted, isInserted2);
    }

    @Test
    @Rollback(value = false)
    void insertContainerTest(){
        ContainerForm containerForm = new ContainerForm();
        containerForm.setTitle("아아");
        containerForm.setUserId(1L);

        boolean isInserted = containerRepostiory.insertContainerByContainerForm(containerForm);

        log.info("isInserted : {}", isInserted);
    }
}