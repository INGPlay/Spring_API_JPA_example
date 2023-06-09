package api.jpa.practice.repository;

import api.jpa.practice.domain.request.RegisterDTO;
import api.jpa.practice.entity.ShortCut;
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
    public boolean insertUserByRegisterDTO(RegisterDTO registerDTO){

        String username = registerDTO.getUsername();
        String password = registerDTO.getPassword();
        UserRole userRole = registerDTO.getUserRole();
        TimeInform timeInform = new TimeInform(new Date(), new Date());

        User user = new User(username, password, userRole, timeInform);

        em.persist(user);

        return true;
    }

    public Optional<User> findUserById(Long id){

        return Optional.ofNullable(em.find(User.class, id));
    }

    public Optional<User> findUserByUsername(String username){

        return em.createQuery(
                        "select u from User u" +
                                " where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream().findFirst();
    }

    public boolean deleteUserByUser(User user){
        em.remove(user);

        return true;
    }
}
