package api.jpa.practice.entity;

import api.jpa.practice.domain.request.RegisterForm;
import api.jpa.practice.entity.embeddables.TimeInform;
import api.jpa.practice.entity.enums.UserRole;
import api.jpa.practice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Date;

@Transactional
@SpringBootTest
@Slf4j
class ContainerTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void setUp() {
        RegisterForm registerForm = new RegisterForm();
        registerForm.setUsername("faraway");
        registerForm.setPassword("dddd");
        registerForm.setUserRole(UserRole.ADMIN);

        RegisterForm registerForm1 = new RegisterForm();
        registerForm1.setUsername("asdf");
        registerForm1.setPassword("aaaa");
        registerForm1.setUserRole(UserRole.NORMAL);

        boolean isInserted = userRepository.insertUserByRegisterForm(registerForm);
        boolean isInserted1 = userRepository.insertUserByRegisterForm(registerForm1);

        log.info("isInserted : {}", isInserted);
        log.info("isInserted1 : {}", isInserted1);
    }

    @Test
    @Rollback(value = false)
    void findTest(){
        User user = userRepository.findUserByUsername("faraway").get();

        TimeInform timeInform = new TimeInform(new Date(), new Date());

        Container container = new Container();
        container.setUser(user);
        container.setTitle("ddd");
        container.setTimeInform(timeInform);

        em.persist(container);
    }


}