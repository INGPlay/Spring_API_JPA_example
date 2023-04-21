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
        boolean isInsertedContainer = containerRepostiory.insertContainerWithUsername(containerForm);

        ContainerDTOWithUsername containerForm2 = new ContainerDTOWithUsername();
        containerForm2.setUsername("faraway2");
        containerForm2.setTitle("faraway2 첫번째 저장소");
        boolean isInsertedContainer2 = containerRepostiory.insertContainerWithUsername(containerForm2);

        ContainerDTOWithUsername containerForm3 = new ContainerDTOWithUsername();
        containerForm3.setUsername("faraway2");
        containerForm3.setTitle("faraway2 두번째 저장소");
        boolean isInsertedContainer3 = containerRepostiory.insertContainerWithUsername(containerForm3);

        log.info("isInsertContainer :{}", isInsertedContainer);
        log.info("isInsertContainer2 :{}", isInsertedContainer2);
        log.info("isInsertContainer3 :{}", isInsertedContainer3);
    }

    @Test
    @Rollback(value = false)
    void insertPostTest(){
        PostDTO postDTO = new PostDTO();
        postDTO.setContainer(containerRepostiory.findContainerById(1L).get());
        postDTO.setTitle("첫번째 글입니다.");
        postDTO.setContent("첫 번째 내용입니다");

        postRepostiory.insertPostByPostForm(postDTO);


        PostDTO postDTO2 = new PostDTO();
        postDTO2.setContainer(containerRepostiory.findContainerById(2L).get());
        postDTO2.setTitle("첫번째 글입니다.");
        postDTO2.setContent("첫 번째 내용입니다");

        postRepostiory.insertPostByPostForm(postDTO2);

        PostDTO postDTO3 = new PostDTO();
        postDTO3.setContainer(containerRepostiory.findContainerById(2L).get());
        postDTO3.setTitle("두번째 글입니다.");
        postDTO3.setContent("두 번째 내용입니다");

        postRepostiory.insertPostByPostForm(postDTO3);
    }
}