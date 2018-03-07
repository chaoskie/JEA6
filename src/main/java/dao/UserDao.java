package dao;

import domain.User;

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
            return null
        }
    }

    public void createUser(User u) {
        em.persist(u);
    }
}
