package api.jpa.practice.repository;

import api.jpa.practice.domain.request.ContainerDTOWithUserId;
import api.jpa.practice.domain.request.RegisterDTO;
import api.jpa.practice.entity.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

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

        log.info("isInserted : {}, isInserted2 : {}", isInserted, isInserted2);
    }

    @Test
    @Rollback(value = false)
    void insertContainerTest(){
        ContainerDTOWithUserId containerDTOWithUserId = new ContainerDTOWithUserId();
        containerDTOWithUserId.setTitle("아아");
        containerDTOWithUserId.setUserId(1L);

        boolean isInserted = containerRepostiory.insertContainerWithUserId(containerDTOWithUserId);

        log.info("isInserted : {}", isInserted);
    }
}