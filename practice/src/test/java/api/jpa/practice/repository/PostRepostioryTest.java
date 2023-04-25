package api.jpa.practice.repository;

import api.jpa.practice.domain.request.RegisterDTO;
import api.jpa.practice.entity.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    }
}