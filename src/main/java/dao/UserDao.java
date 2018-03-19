package dao;

import domain.User;

import java.util.List;

public interface UserDao {
    public List<User> getUsers();

    public User getUser(int id);

    public User getUserByName(String name);

    public List<User> getFollowers(User user);

    public User createUser(User u);

    public void followUser(User u, User toFollow);

    public void unfollowUser(User u, User toUnfollow);

    public boolean login(String username, String password);

    public void updateBio(User user, String bio);

    public void updateLocation(User user, String location);
}
