package api.jpa.practice.repository;

import api.jpa.practice.domain.form.PostForm;
import api.jpa.practice.domain.request.ContainerPathDTO;
import api.jpa.practice.domain.request.PostDTO;
import api.jpa.practice.entity.Container;
import api.jpa.practice.entity.Post;
import api.jpa.practice.entity.embeddables.TimeInform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepostiory {

    private final EntityManager em;
    private final ContainerRepostiory containerRepostiory;

    public boolean insertPostByPostForm(PostDTO postDTO){

        try{
            Container container  = postDTO.getContainer();
            String title = postDTO.getPostForm().getPostTitle();
            String content = postDTO.getPostForm().getPostContent();
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

    public List<Post> findPostsByContainerForm(ContainerPathDTO containerPathDTO){
        return em.createQuery(
                "select p from Post p" +
                        " where p.container.user.username = :username and p.container.title = :containerTitle", Post.class)
                .setParameter("username", containerPathDTO.getUsername())
                .setParameter("containerTitle", containerPathDTO.getContainerTitle())
                .getResultList();
    }

    public List<Post> searchPosts(Container container, String postKeyword){
        return em.createQuery(
                "select p from Post p" +
                        " where p.container = :container and p.title like concat('%', :postKeyword, '%') ", Post.class)
                .setParameter("container", container)
                .setParameter("postKeyword", postKeyword)
                .getResultList();
    }

    public Optional<Post> findPost(Container container, String postTitle){
        return em.createQuery(
                "select p from Post p" +
                        " where p.container = :container and p.title = :postTitle", Post.class)
                .setParameter("container", container)
                .setParameter("postTitle", postTitle)
                .getResultStream()
                .findAny();
    }

    public List<Post> findPostByContainerId(Long containerId){
        return em.createQuery(
                "select c.posts from Container c" +
                        " where c.containerId = :containerId", Post.class)
                .setParameter("containerId", containerId)
                .getResultList();
    }

    public boolean deletePost(Post post){
        try {
            em.remove(post);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Transactional
    public boolean updatePost(Post post, PostForm postForm){
        try {
            post.setTitle(postForm.getPostTitle());
            post.setContent(postForm.getPostContent());
            post.getTimeInform().renewUpdatedTiem();
            em.persist(post);

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
