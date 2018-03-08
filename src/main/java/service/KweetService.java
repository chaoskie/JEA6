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
        User user = userDao.getUser(u.getId());

        if (user == null) {
            throw new NotFoundException("User does not exist");
        }

        if (s.length() == 0 || s.length() > 140) {
            throw new IllegalArgumentException("Message is invalid");
        }

        Kweet kweet = new Kweet(user, s);

        return kweetDao.createKweet(kweet);
    }

    public void deleteKweet(int id) {
        Kweet k = kweetDao.getKweetById(id);

        if (k == null) {
            throw new IllegalArgumentException("Kweet does not exist");
        }

        // Check user in session
        //

        kweetDao.deleteKweet(k);
    }
}
