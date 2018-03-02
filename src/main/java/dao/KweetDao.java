package dao;

import domain.Kweet;

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
    EntityManager em;

    public KweetDao() { }

    public List<Kweet> getKweets() {
        TypedQuery<Kweet> query = em.createQuery("SELECT k FROM Kweet k", Kweet.class);
        return query.getResultList();
    }
}
