package api.jpa.practice.repository;

import api.jpa.practice.domain.request.RegisterForm;
import api.jpa.practice.entity.User;
import api.jpa.practice.entity.embeddables.TimeInform;
import api.jpa.practice.entity.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;
    public boolean insertUserByRegisterForm(RegisterForm registerForm){

        try {
            String username = registerForm.getUsername();
            String password = registerForm.getPassword();
            UserRole userRole = registerForm.getUserRole();
            TimeInform timeInform = new TimeInform(new Date(), new Date());

            User user = new User(username, password, userRole, timeInform);

            em.persist(user);

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Optional<User> findUserById(Long id){

        return Optional.ofNullable(em.find(User.class, id));
    }

    public Optional<User> findUserByUsername(String username){

        User user = em.createQuery(
                "select u from User u" +
                        " where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();

        return Optional.ofNullable(user);
    }
}
