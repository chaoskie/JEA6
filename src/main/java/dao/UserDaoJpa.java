package dao;

import domain.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@JPA
public class UserDaoJpa implements UserDao {
    @PersistenceContext(unitName = "Kwetter")
    private EntityManager em;

    @Override
    public void createUser(User user) {
        em.persist(user);
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public void removeUser(User user) {
        em.remove(em.merge(user));
    }

    @Override
    public List<User> getAllUsers() {
        Query query = em.createQuery("SELECT u FROM User u");
        return query.getResultList();
    }

    @Override
    public User findByName(String username) {
        TypedQuery<User> query = em.createNamedQuery("user.findByName", User.class);
        query.setParameter("name", username);

        List<User> result = query.getResultList();
        return result.get(0);
    }

    @Override
    public User findById(int id) {
        return em.find(User.class, id);
    }

    @Override
    public void followUser(User user, User following) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void unfollowUser(User user, User following) {
        throw new UnsupportedOperationException();
    }
}
