package service;

import dao.JPA;
import dao.KweetDao;
import dao.UserDao;
import domain.Kweet;
import domain.User;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
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

        // TODO: Process mentions

        return kweetDao.createKweet(kweet);
    }

    public void deleteKweet(int id) {
        Kweet k = kweetDao.getKweetById(id);

        if (k == null) {
            throw new IllegalArgumentException("Kweet does not exist");
        }

        // TODO: Check user in session
        //

        kweetDao.deleteKweet(k);
    }

    public int likeKweet(int kweetId) {
        // TODO: Temporary, replace with user session
        User u = userDao.getUser(1);
        Kweet k = kweetDao.getKweetById(kweetId);

        if (k == null) {
            throw new NotFoundException("Kweet does not exist");
        }

        if (k.getLikes().contains(u)) {
            throw new IllegalArgumentException("User already liked this kweet");
        }

        return kweetDao.likeKweet(k, u);
    }

    public int unlikeKweet(int kweetId) {
        // TODO: Temporary, replace with user session
        User u = userDao.getUser(1);
        Kweet k = kweetDao.getKweetById(kweetId);

        if (k == null) {
            throw new NotFoundException("Kweet does not exist");
        }

        if (k.getLikes().contains(u)) {
            throw new IllegalArgumentException("User didn't like this kweet");
        }

        return kweetDao.unlikeKweet(k, u);
    }
}
