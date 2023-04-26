package api.jpa.practice.repository;

import api.jpa.practice.domain.form.ContainerForm;
import api.jpa.practice.domain.request.ContainerDTO;
import api.jpa.practice.domain.request.PagingDTO;
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

    public boolean insertContainer(ContainerDTO containerPathDTO){

        try {
            User user = containerPathDTO.getUser();
            String title = containerPathDTO.getContainerTitle();

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

    public boolean deleteContainer(Container container){
        try {
            em.remove(container);

            return true;
        } catch (Exception e){
            return false;
        }

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

    public List<Container> findContainersByUser(User user, PagingDTO pagingDTO){

        try {
            List<Container> containers = em.createQuery(
                            "select c from Container c" +
                                    " where c.user = :user", Container.class)
                    .setParameter("user", user)
                    .setFirstResult(pagingDTO.getStartPos())
                    .setMaxResults(pagingDTO.getLength())
                    .getResultList();

            return containers;

        } catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public Optional<Container> findContainer(ContainerDTO containerDTO){
        return em.createQuery(
                "select c from Container c" +
                        " where c.user = :user and c.title = :containerTitle", Container.class)
                .setParameter("user", containerDTO.getUser())
                .setParameter("containerTitle", containerDTO.getContainerTitle())
                .getResultStream().findAny();
    }

    public List<Container> searchContainers(User user, String title){
        try{
            List<Container> containers = em.createQuery(
                    "select c from Container c" +
                            " where c.user = :user and c.title like concat('%', :title, '%')", Container.class)
                    .setParameter("user", user)
                    .setParameter("title", title)
                    .getResultList();

            return containers;
        } catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Container> searchContainers(User user, String title, PagingDTO pagingDTO){
        try{
            List<Container> containers = em.createQuery(
                            "select c from Container c" +
                                    " where c.user = :user and c.title like concat('%', :title, '%')", Container.class)
                    .setParameter("user", user)
                    .setParameter("title", title)
                    .setFirstResult(pagingDTO.getStartPos())
                    .setMaxResults(pagingDTO.getLength())
                    .getResultList();

            return containers;
        } catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean updateContainer(Container container, ContainerForm containerForm){
        try {
            container.setTitle(containerForm.getContainerTitle());
            container.getTimeInform().renewUpdatedTiem();
            em.persist(container);

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return  true;
    }
}
