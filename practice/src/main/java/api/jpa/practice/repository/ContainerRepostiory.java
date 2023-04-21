package api.jpa.practice.repository;

import api.jpa.practice.domain.request.ContainerDTO;
import api.jpa.practice.domain.request.ContainerDTOWithUserId;
import api.jpa.practice.domain.request.ContainerDTOWithUsername;
import api.jpa.practice.entity.Container;
import api.jpa.practice.entity.User;
import api.jpa.practice.entity.embeddables.TimeInform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ContainerRepostiory {
    private final EntityManager em;
    private final UserRepository userRepository;

    public boolean insertContainerByContainerForm(ContainerDTO containerDTO){
        try {
            // user 준비
            User user = containerDTO.getUser();

            // title 준비
            String title = containerDTO.getTitle();

            // timeInform 준비
            TimeInform timeInform = new TimeInform(new Date(), new Date());

            // -> Container 생성
            Container container = new Container(user, timeInform, title);

            em.persist(container);

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
    public boolean insertContainerWithUserId(ContainerDTOWithUserId containerDTOWithUserId){

        try {
            Long userId = containerDTOWithUserId.getUserId();
            
            // title 준비
            String title = containerDTOWithUserId.getTitle();

            // user 준비
            User user = em.find(User.class, userId);

            // timeInform 준비
            TimeInform timeInform = new TimeInform(new Date(), new Date());

            // -> Container 생성
            Container container = new Container(user, timeInform, title);
            
            em.persist(container);
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean insertContainerWithUsername(ContainerDTOWithUsername containerDTOWithUsername){

        try {
            String username = containerDTOWithUsername.getUsername();

            // title 준비
            String title = containerDTOWithUsername.getTitle();

            // user 준비
            User user = userRepository.findUserByUsername(username).orElseThrow(()-> {
                return new RuntimeException("username으로 User를 찾을 수 없음");
            });

            // timeInform 준비
            TimeInform timeInform = new TimeInform(new Date(), new Date());

            // -> Container 생성
            Container container = new Container(user, timeInform, title);

            em.persist(container);

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Optional<Container> findContainerById(Long containerId){
        return Optional.ofNullable(
                em.find(Container.class, containerId)
        );
    }

    public List<Container> findContainersByUser(User user){

        try {
            List<Container> containers = em.createQuery(
                            "select c from Container c" +
                                    " where c.user = :user", Container.class)
                    .setParameter("user", user)
                    .getResultList();

            return containers;

        } catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
