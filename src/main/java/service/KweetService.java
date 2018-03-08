package service;

import dao.JPA;
import dao.KweetDao;
import dao.UserDao;
import domain.Kweet;
import domain.User;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.List;

public class KweetService {

    @Inject @JPA
    UserDao userDao;

    @Inject @JPA
    KweetDao kweetDao;

    public List<Kweet> getKweets() {
        return kweetDao.getKweets();
    }

    public List<Kweet> getKweetsByUser(String username) {
        User user = userDao.getUserByName(username);

        if (user == null) {
            throw new NotFoundException("User " + username + " was not found");
        }

        return kweetDao.getKweetsByUser(user);
    }

    public List<Kweet> getTimeline(String username) {
        User user = userDao.getUserByName(username);

        if (user == null) {
            throw new NotFoundException("User " + username + " was not found");
        }

        return kweetDao.getTimeline(user);
    }

    public Kweet createKweet(User u, String s) {
        Kweet kweet = new Kweet(u, s);

        return kweetDao.createKweet(kweet);
    }
}
