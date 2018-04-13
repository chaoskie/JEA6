package dao;

import domain.Kweet;
import domain.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@JPA
@Transactional
@Stateless
public class KweetDaoJPA implements KweetDao {

    @Inject @JPA
    private UserDao userDao;

    @PersistenceContext(unitName = "kwetterPU")
    private EntityManager em;

    public KweetDaoJPA() { }

    public List<Kweet> getKweets() {
        TypedQuery<Kweet> query = em.createQuery("SELECT k FROM Kweet k", Kweet.class);
        return query.getResultList();
    }

    public List<Kweet> searchKweets(String content) {
        TypedQuery<Kweet> query = em.createQuery("SELECT k FROM Kweet k WHERE k.message LIKE :content", Kweet.class);
        query.setParameter("content", content);
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
        List<Kweet> kweets = getKweetsByUser(user);
        for(User u : user.getFollowing()) {
            kweets.addAll(getKweetsByUser(u));
        }

        // Sort by date in reverse order
        kweets.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

        return kweets;
    }

    public Kweet createKweet(Kweet kweet) {
        em.persist(kweet);
        return kweet;
    }

    public void deleteKweet(Kweet k) {
        if (!em.contains(k)) {
            k = em.merge(k);
        }

        em.remove(k);
    }

    public Kweet likeKweet(Kweet k, User u) {
        k.addLike(u);
        em.merge(k);

        return k;
    }

    public Kweet unlikeKweet(Kweet k, User u) {
        k.removeLike(u);
        em.merge(k);

        return k;
    }
}
