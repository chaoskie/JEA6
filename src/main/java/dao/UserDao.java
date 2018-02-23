package dao;

import domain.User;

import java.util.List;

public interface UserDao {
    void createUser(User user);
    void updateUser(User user);
    void removeUser(User user);

    List<User> getAllUsers();

    User findByName(String username);
    User findById(int id);

    void followUser(User user, User following);
    void unfollowUser(User user, User following);
}
