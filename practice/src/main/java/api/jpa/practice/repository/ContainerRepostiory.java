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
            
            // title 준비
            String title = containerForm.getTitle();

            // user 준비
            User user = em.find(User.class, userId);

            // Key 준비
            ContainerIds containerIds = getContainerIds(userId);

            // timeInform 준비
            TimeInform timeInform = new TimeInform(new Date(), new Date());

            // -> Container 생성
            Container container = new Container(user, containerIds, timeInform, title);
            
            em.persist(container);
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }


    private ContainerIds getContainerIds(Long userId) {
        ContainerIds containerIds = new ContainerIds();
        containerIds.setUserId(userId);

        // ContainerIndex 1 증가
        Long containerIndex = sequenceRepository.plusOneContainerIndexByUserId(userId);
        containerIds.setContainerId(containerIndex);
        return containerIds;
    }
}
