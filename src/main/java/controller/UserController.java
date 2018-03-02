package controller;

import domain.Role;
import domain.User;
import service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("users")
public class UserController extends Application {

    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") int id) {
        return userService.getUser(id);
    }

    @GET
    @Path("demoAdd")
    @Produces(MediaType.TEXT_PLAIN)
    public String demoAdd() {
        User u = new User("JohnDoe", "password", Role.User, "John Doe", "", "", "", "");
        userService.createUser(u);

        return "ok";
    }
}
