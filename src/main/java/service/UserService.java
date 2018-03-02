package service;

import dao.JPA;
import dao.KweetDao;
import dao.UserDao;
import domain.User;

import javax.inject.Inject;
import java.util.List;

public class UserService {

    @Inject @JPA
    UserDao userDao;

    @Inject @JPA
    KweetDao kweetDao;


    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User getUser(int id) {
        return userDao.getUser(id);
    }

    public void createUser(User u) {
        userDao.createUser(u);
    }
}
