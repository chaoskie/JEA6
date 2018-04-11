package dao;

import domain.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();

    User getUser(int id);

    User getUserByName(String name);

    List<User> getFollowers(User user);

    User createUser(User u);

    void followUser(User u, User toFollow);

    void unfollowUser(User u, User toUnfollow);

    boolean login(String username, String password);

    void updateBio(User user, String bio);

    void updateLocation(User user, String location);

    void updateWebsite(User user, String website);

    void updateDisplayname(User user, String displayname);
}
