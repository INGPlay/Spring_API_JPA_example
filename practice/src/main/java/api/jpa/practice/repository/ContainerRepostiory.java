package api.jpa.practice.repository;

import api.jpa.practice.domain.request.ContainerForm;
import api.jpa.practice.entity.Container;
import api.jpa.practice.entity.User;
import api.jpa.practice.entity.embeddables.ContainerIds;
import api.jpa.practice.entity.embeddables.TimeInform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ContainerRepostiory {
    private final EntityManager em;
    private final SequenceRepository sequenceRepository;

    public boolean insertContainerByContainerForm(ContainerForm containerForm){

        try {
            Long userId = containerForm.getUserId();
            String title = containerForm.getTitle();

            // Key 준비
            ContainerIds containerIds = new ContainerIds();
            containerIds.setUserId(userId);    // User 와 연관관계
            //      ContainerIndex 1 증가
            containerIds.setContainerId(sequenceRepository.plusOneContainerIndexByUserId(userId));

            // timeInform 준비
            TimeInform timeInform = new TimeInform(new Date(), new Date());

            // Container 생성
            Container container = new Container();

            container.setContainerIds(containerIds);
            container.setTimeInform(timeInform);
            container.setTitle(title);
            container.setUser(em.find(User.class, userId));

            em.persist(container);
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
