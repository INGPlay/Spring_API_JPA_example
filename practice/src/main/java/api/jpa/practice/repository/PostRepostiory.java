package api.jpa.practice.repository;

import api.jpa.practice.domain.request.PostDTO;
import api.jpa.practice.entity.Container;
import api.jpa.practice.entity.Post;
import api.jpa.practice.entity.embeddables.TimeInform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepostiory {

    private final EntityManager em;

    public boolean insertPostByPostForm(PostDTO postDTO){

        try{
            Container container  = postDTO.getContainer();
            String title = postDTO.getTitle();
            String content = postDTO.getContent();
            TimeInform timeInform = new TimeInform(new Date(), new Date());

            Post post = new Post();
            post.setContainer(container);
            post.setTitle(title);
            post.setContent(content);
            post.setTimeInform(timeInform);

            em.persist(post);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public List<Post> findPostByUsername(String username){
        return em.createQuery(
                "select u.posts from User u" +
                        " where u.username = :username", Post.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Post> findPostByContainerId(Long containerId){
        return em.createQuery(
                "select c.posts from Container c" +
                        " where c.containerId = :containerId", Post.class)
                .setParameter("containerId", containerId)
                .getResultList();
    }
}
