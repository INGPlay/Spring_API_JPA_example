package api.jpa.practice.entity;

import api.jpa.practice.domain.request.RegisterDTO;
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
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("faraway");
        registerDTO.setPassword("dddd");
        registerDTO.setUserRole(UserRole.ADMIN);

        RegisterDTO registerDTO1 = new RegisterDTO();
        registerDTO1.setUsername("asdf");
        registerDTO1.setPassword("aaaa");
        registerDTO1.setUserRole(UserRole.NORMAL);

        boolean isInserted = userRepository.insertUserByRegisterDTO(registerDTO);
        boolean isInserted1 = userRepository.insertUserByRegisterDTO(registerDTO1);

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