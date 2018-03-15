package service;

import dao.JPA;
import dao.KweetDao;
import dao.UserDao;
import domain.User;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

public class UserService {

    @Inject @JPA
    UserDao userDao;

    @Inject @JPA
    KweetDao kweetDao;


    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User getUser(int id) throws IllegalArgumentException, NotFoundException {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }

        User user = userDao.getUser(id);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return userDao.getUser(id);
    }

    public User getUserByName(String name) throws IllegalArgumentException, NotFoundException {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty");
        }

        User user = userDao.getUserByName(name);

        if (user == null) {
            throw new NotFoundException("User " + name + " was not found");
        }

        return user;
    }

    public User createUser(User u) throws IllegalArgumentException {
        if (u.getPassword().isEmpty() || u.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Required fields (username, password) were invalid");
        }

        u.setPassword(generateSha512(u.getPassword()));

        return userDao.createUser(u);
    }

    public void followUser(User user, String username) throws NotFoundException {
        User toFollow = userDao.getUserByName(username);

        if (toFollow == null) {
            throw new NotFoundException("User " + username + " was not found");
        }

        userDao.followUser(user, toFollow);
    }

    public void unfollowUser(User user, String username) throws NotFoundException {
        User toUnfollow = userDao.getUserByName(username);

        if (toUnfollow == null) {
            throw new NotFoundException("User " + username + " was not found");
        }

        userDao.unfollowUser(user, toUnfollow);
    }

    public List<User> getFollowers(String name) throws NotFoundException {
        User user = userDao.getUserByName(name);

        if (user == null) {
            throw new NotFoundException("User " + name + " was not found");
        }

        return userDao.getFollowers(user);
    }

    public List<User> getFollowing(String name) throws NotFoundException {
        User user = userDao.getUserByName(name);

        if (user == null) {
            throw new NotFoundException("User " + name + " was not found");
        }

        return user.getFollowing();
    }

    public void updateBio(User user, String bio) throws IllegalArgumentException {
        if (user == null || user.getId() <= 0) {
            throw new IllegalArgumentException("User was invalid");
        }

        userDao.updateBio(user, bio);
    }

    public void updateLocation(User user, String location) throws IllegalArgumentException {
        if (user == null || user.getId() <= 0) {
            throw new IllegalArgumentException("User was invalid");
        }

        userDao.updateLocation(user, location);
    }

    public boolean login(String username, String password) {
        return userDao.login(username, generateSha512(password));
    }

    public String generateSha512(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            return text;
        }
    }
}
