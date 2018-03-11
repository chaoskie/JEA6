package dao;

import domain.User;
import util.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@JPA
@Transactional
public class UserDao {

    @PersistenceContext(unitName = "kwetterPU")
    private EntityManager em;

    public UserDao() { }

    public List<User> getUsers() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public User getUser(int id) {
        return em.find(User.class, id);
    }

    public User getUserByName(String name) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :name", User.class);
        query.setParameter("name", name);

        try {
            return query.getSingleResult();
        }
        catch(NoResultException e)
        {
            return null;
        }
    }

    public List<User> getFollowers(User user) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE :user MEMBER OF u.following", User.class);
        query.setParameter("user", user);

        return query.getResultList();
    }

    public User createUser(User u) {
        em.persist(u);
        em.flush();

        return u;
    }

    public void followUser(User u, User toFollow) {
        u.followUser(toFollow);
        em.merge(u);
    }

    public void unfollowUser(User u, User toUnfollow) {
        u.unfollowUser(toUnfollow);
        em.merge(u);
    }

    public boolean login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class).getSingleResult();
            if (user != null) {
                return true;
            }
        } catch (NoResultException e) {
            Logger.log(e);
            return false;
        }

        return false;
    }

    public void updateBio(User user, String bio) {
        user.setBio(bio);
        em.merge(user);
    }

    public void updateLocation(User user, String location) {
        user.setLocation(location);
        em.merge(user);
    }
}
