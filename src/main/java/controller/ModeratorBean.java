package controller;

import domain.User;
import service.UserService;

import javax.inject.Inject;
import java.util.List;

@javax.faces.bean.ManagedBean(name = "moderatorBean", eager = true)
public class ModeratorBean {

    @Inject
    private UserService userService;

    public List<User> getUsers() {
        return userService.getUsers();
    }
}
