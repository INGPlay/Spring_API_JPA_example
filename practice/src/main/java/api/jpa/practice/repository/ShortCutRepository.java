package api.jpa.practice.repository;

import api.jpa.practice.domain.request.PagingDTO;
import api.jpa.practice.domain.request.ShortCutDTO;
import api.jpa.practice.entity.Post;
import api.jpa.practice.entity.ShortCut;
import api.jpa.practice.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ShortCutRepository {
    private final EntityManager em;

    public boolean insertShortCut(ShortCutDTO shortCutDTO){
        try {
            ShortCut shortCut = new ShortCut();
            shortCut.setUser(shortCutDTO.getUser());
            shortCut.setPost(shortCutDTO.getPost());

            em.persist(shortCut);

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public List<ShortCut> findShortCuts(String username){
        return em.createQuery(
                "select s from ShortCut s" +
                        " join fetch s.post p" +
                        " where s.user.username = :username", ShortCut.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<ShortCut> findShortCuts(String username, PagingDTO pagingDTO){
        return em.createQuery(
                        "select s from ShortCut s" +
                                " join fetch s.post p" +
                                " where s.user.username = :username", ShortCut.class)
                .setParameter("username", username)
                .setFirstResult(pagingDTO.getStartPos())
                .setMaxResults(pagingDTO.getLength())
                .getResultList();
    }
    public Optional<ShortCut> findShortCutByPost(Post post){
        return em.createQuery(
                "select s from ShortCut s" +
                        " where s.post = :post", ShortCut.class)
                .setParameter("post", post)
                .getResultStream()
                .findAny();
    }

    public Optional<ShortCut> findShortCutWithUser(User user, Long postId){
        return em.createQuery(
                "select s from ShortCut s" +
                        " where s.user = :user and s.post.id = :postId", ShortCut.class)
                .setParameter("user", user)
                .setParameter("postId", postId)
                .getResultStream()
                .findAny();
    }

    public boolean deleteShortCut(ShortCut shortCut){
        try {
            em.remove(shortCut);

        } catch (Exception e){
            return false;
        }

        return true;
    }
}
