package api.jpa.practice.repository;

import api.jpa.practice.domain.request.ContainerDTOWithUsername;
import api.jpa.practice.domain.request.PostDTO;
import api.jpa.practice.domain.request.RegisterDTO;
import api.jpa.practice.entity.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Slf4j
class PostRepostioryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContainerRepostiory containerRepostiory;
    @Autowired
    private PostRepostiory postRepostiory;


    @BeforeEach
    void setUp() {
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


        ContainerDTOWithUsername containerForm = new ContainerDTOWithUsername();
        containerForm.setUsername("faraway");
        containerForm.setTitle("첫 번째 저장소");
        boolean isInsertedContainer = containerRepostiory.insertContainer(containerForm);

        ContainerDTOWithUsername containerForm2 = new ContainerDTOWithUsername();
        containerForm2.setUsername("faraway2");
        containerForm2.setTitle("faraway2 첫번째 저장소");
        boolean isInsertedContainer2 = containerRepostiory.insertContainer(containerForm2);

        ContainerDTOWithUsername containerForm3 = new ContainerDTOWithUsername();
        containerForm3.setUsername("faraway2");
        containerForm3.setTitle("faraway2 두번째 저장소");
        boolean isInsertedContainer3 = containerRepostiory.insertContainer(containerForm3);

        log.info("isInsertContainer :{}", isInsertedContainer);
        log.info("isInsertContainer2 :{}", isInsertedContainer2);
        log.info("isInsertContainer3 :{}", isInsertedContainer3);
    }
}