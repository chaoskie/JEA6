package service;

import dao.*;
import domain.Kweet;
import domain.Role;
import domain.User;
import Exceptions.*;

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

    public List<Kweet> searchKweets(String c) throws NotFoundException { return kweetDao.searchKweets(c);}

    public List<Kweet> getKweetsByUser(String username) throws UserNotFoundException {
        User user = userDao.getUserByName(username);

        if (user == null) {
            throw new UserNotFoundException("User " + username + " was not found");
        }

        return kweetDao.getKweetsByUser(user);
    }

    public List<Kweet> getTimeline(String username) throws UserNotFoundException {
        User user = userDao.getUserByName(username);

        if (user == null) {
            throw new UserNotFoundException("User " + username + " was not found");
        }

        return kweetDao.getTimeline(user);
    }

    public Kweet createKweet(User u, String s) throws KweetNotValidException, UserNotFoundException {
        User user = userDao.getUser(u.getId());

        if (user == null) {
            throw new UserNotFoundException("User does not exist");
        }

        if (s.length() == 0 || s.length() > 140) {
            throw new KweetNotValidException("Message is invalid");
        }

        Kweet kweet = new Kweet(user, s);

        // TODO: Process mentions


        return kweetDao.createKweet(kweet);
    }

    public void deleteKweet(User user, int id) throws InvalidActionException, KweetNotFoundException {
        Kweet k = kweetDao.getKweetById(id);

        if (k == null) {
            throw new KweetNotFoundException("Kweet does not exist");
        }

        if (k.getUser().getId() != user.getId() && !user.getRole().contains(Role.Moderator)) {
            // Moderators and Administrators are allowed to delete all kweets regardless of ownership
            // Users however can only delete their own kweets
            throw new InvalidActionException("User is not allowed to delete this kweet");
        }

        kweetDao.deleteKweet(k);
    }

    public int likeKweet(User user, int kweetId) throws InvalidActionException, KweetNotFoundException {
        Kweet k = kweetDao.getKweetById(kweetId);

        if (k == null) {
            throw new KweetNotFoundException("Kweet does not exist");
        }

        if (k.getLikes().contains(user)) {
            throw new InvalidActionException("User already liked this kweet");
        }

        return kweetDao.likeKweet(k, user);
    }

    public int unlikeKweet(User user, int kweetId) throws InvalidActionException, KweetNotFoundException {
        Kweet k = kweetDao.getKweetById(kweetId);

        if (k == null) {
            throw new KweetNotFoundException("Kweet does not exist");
        }

        if (!k.getLikes().contains(user)) {
            throw new InvalidActionException("User didn't like this kweet");
        }

        return kweetDao.unlikeKweet(k, user);
    }
}
