package service;

import dao.JPA;
import dao.KweetDao;
import dao.UserDao;
import domain.Kweet;

import javax.inject.Inject;
import java.util.List;

public class KweetService {

    @Inject @JPA
    UserDao userDao;

    @Inject @JPA
    KweetDao kweetDao;


    public List<Kweet> getKweets() {
        return kweetDao.getKweets();
    }
}
