package no.woact.dao;

import no.woact.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * @author Espen Rønning
 * @version 2016-05-28
 * @since 1.8
 */
@Stateless
public class UserDao implements Serializable {

    @PersistenceContext(unitName = "pg6100_exam")
    private EntityManager entityManager;


    public User getByUsername(String username) {
        List<User> users = entityManager.createNamedQuery(User.GET_BY_USERNAME, User.class).setParameter("username", username).setMaxResults(1).getResultList();
        if (users.size() != 0) {
            return users.get(0);
        }
        return null;
    }

    public User persist(User user) {
        user.setEncryptedPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        entityManager.persist(user);
        return user;
    }
}
