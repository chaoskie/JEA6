package service;

import com.google.gson.Gson;
import dao.*;
import domain.Role;
import domain.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

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

    private String secretKey = "kwetterSecretKeyDeluxe";

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

        u.setPassword(generateSha256(u.getPassword()));

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

    public void updateWebsite(User user, String website) {
        if (user == null || user.getId() <= 0) {
            throw new IllegalArgumentException("User was invalid");
        }

        userDao.updateWebsite(user, website);
    }

    public void updateDisplayname(User user, String displayname) {
        if (user == null || user.getId() <= 0 || displayname.isEmpty()) {
            throw new IllegalArgumentException("User was invalid");
        }

        userDao.updateDisplayname(user, displayname);
    }

    public boolean login(String username, String password) {
        return userDao.login(username, generateSha256(password));
    }

    public String generateSha256(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            return text;
        }
    }

    public String issueToken(String username) {
        User user = getUserByName(username);

        JwtBuilder builder = Jwts.builder().claim("username", username).signWith(SignatureAlgorithm.HS512, secretKey);

        if(user.getRole().contains(Role.Moderator)) {
            builder.claim("moderator", true);
        }

        if(user.getRole().contains(Role.Administrator)) {
            builder.claim("administrator", true);
        }

        return builder.compact();
    }

    public User parseUserFromToken(String token) {
        DefaultClaims t = (DefaultClaims) Jwts.parser().setSigningKey(secretKey).parse(token).getBody();
        return getUserByName((String) t.get("username"));
    }
}
