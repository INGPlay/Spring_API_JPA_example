package api.jpa.practice;

import api.jpa.practice.entity.User;
import api.jpa.practice.entity.embeddables.TimeInform;
import api.jpa.practice.entity.sequenceTables.ContainerSequence;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.ContinueResponseTiming;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
class PracticeApplicationTests {
    @Autowired
    private EntityManager em;

    @Test
    void contextLoads() {
        Date createdDate = new Date();
        Date updatedDate = new Date();

        TimeInform timeInform = new TimeInform(createdDate, updatedDate);

        User user = new User();
        user.setUsername("far");
        user.setPassword("admin");
        user.setTimeInform(timeInform);

        em.persist(user);

        User user1 = em.find(User.class, 1L);

        assertThat(createdDate).isEqualTo(user1.getTimeInform().getCreatedTime());
        assertThat(updatedDate).isEqualTo(user1.getTimeInform().getUpdatedTime());
    }

    @Test
    void testSequence(){
    }

}
