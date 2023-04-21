package api.jpa.practice.repository;

import api.jpa.practice.domain.request.ContainerFormWithUsername;
import api.jpa.practice.domain.request.PostForm;
import api.jpa.practice.domain.request.RegisterForm;
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


        ContainerFormWithUsername containerForm = new ContainerFormWithUsername();
        containerForm.setUsername("faraway");
        containerForm.setTitle("첫 번째 저장소");
        boolean isInsertedContainer = containerRepostiory.insertContainerWithUsername(containerForm);

        ContainerFormWithUsername containerForm2 = new ContainerFormWithUsername();
        containerForm2.setUsername("faraway2");
        containerForm2.setTitle("faraway2 첫번째 저장소");
        boolean isInsertedContainer2 = containerRepostiory.insertContainerWithUsername(containerForm2);

        ContainerFormWithUsername containerForm3 = new ContainerFormWithUsername();
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
        PostForm postForm = new PostForm();
        postForm.setContainer(containerRepostiory.findContainerById(1L).get());
        postForm.setTitle("첫번째 글입니다.");
        postForm.setContent("첫 번째 내용입니다");

        postRepostiory.insertPostByPostForm(postForm);


        PostForm postForm2 = new PostForm();
        postForm2.setContainer(containerRepostiory.findContainerById(2L).get());
        postForm2.setTitle("첫번째 글입니다.");
        postForm2.setContent("첫 번째 내용입니다");

        postRepostiory.insertPostByPostForm(postForm2);

        PostForm postForm3 = new PostForm();
        postForm3.setContainer(containerRepostiory.findContainerById(2L).get());
        postForm3.setTitle("두번째 글입니다.");
        postForm3.setContent("두 번째 내용입니다");

        postRepostiory.insertPostByPostForm(postForm3);
    }
}