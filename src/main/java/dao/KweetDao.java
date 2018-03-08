package dao;

import domain.Kweet;
import domain.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@JPA
@Transactional
public class KweetDao {

    @Inject @JPA
    private UserDao userDao;

    @PersistenceContext(unitName = "kwetterPU")
    private EntityManager em;

    public KweetDao() { }

    public List<Kweet> getKweets() {
        TypedQuery<Kweet> query = em.createQuery("SELECT k FROM Kweet k", Kweet.class);
        return query.getResultList();
    }

    public Kweet getKweetById(int id) {
        return em.find(Kweet.class, id);
    }

    public List<Kweet> getKweetsByUser(User user) {
        TypedQuery<Kweet> query = em.createQuery("SELECT k FROM Kweet k WHERE k.user = :user", Kweet.class);
        query.setParameter("user", user);

        return query.getResultList();
    }

    public List<Kweet> getTimeline(User user) {
        /*
        SELECT k.*, uu.* FROM kweet k
        LEFT JOIN users_users uu ON uu.following_ID = k.USER_ID
        WHERE k.USER_ID = 1 OR uu.User_ID = 1
         */
        TypedQuery<Kweet> query = em.createQuery("SELECT k from Kweet k LEFT JOIN k.user u WHERE k.user = :user OR u = :user ORDER BY k.id DESC", Kweet.class);
        query.setParameter("user", user);

        return query.getResultList();
    }

    public Kweet createKweet(Kweet kweet) {
        em.persist(kweet);
        return kweet;
    }

    public void deleteKweet(Kweet k) {
        em.remove(k);
    }

    public int likeKweet(Kweet k, User u) {
        k.addLike(u);
        em.merge(k);

        return k.getLikes().size();
    }

    public int unlikeKweet(Kweet k, User u) {
        k.removeLike(u);
        em.merge(k);

        return k.getLikes().size();
    }
}
