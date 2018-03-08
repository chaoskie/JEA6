package controller;

import domain.Role;
import domain.User;
import service.KweetService;
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

    @Inject
    KweetService kweetService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("username") String name) {
        return userService.getUserByName(name);
    }

    @GET
    @Path("demoAdd")
    @Produces(MediaType.TEXT_PLAIN)
    public String demoAdd() {
        User u = new User("JohnDoe", "password", Role.User, "John Doe", "", "", "", "");
        User u2 = new User("AliceWonderland", "password", Role.User, "Alice W.", "", "I'm Alice!", "Wonderland", "");
        u = userService.createUser(u);
        u2 = userService.createUser(u2);

        kweetService.createKweet(u, "1 This is a test kweet by " + u.getDisplayname());
        kweetService.createKweet(u, "2 This is another test kweet by " + u.getDisplayname());
        kweetService.createKweet(u2, "3 Hello I'm Alice");
        kweetService.createKweet(u2, "4 How are you doing?");

        return "ok";
    }
}
