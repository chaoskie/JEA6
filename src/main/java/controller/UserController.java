package controller;

import domain.Kweet;
import domain.Role;
import domain.User;
import service.KweetService;
import service.UserService;

import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
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
    @Path("{username}/followers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getFollowers(@PathParam("username") String name) {
        return userService.getFollowers(name);
    }

    @GET
    @Path("{username}/following")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getFollowing(@PathParam("username") String name) {
        return userService.getFollowing(name);
    }

    @POST
    @Path("{username}/follow")
    public void followUser(@PathParam("username") String username) {
        User user = getUserFromSession();
        userService.followUser(user, username);
    }

    @POST
    @Path("{username}/unfollow")
    public void unfollowUser(@PathParam("username") String username) {
        User user = getUserFromSession();
        userService.unfollowUser(user, username);
    }

    @PUT
    @Path("{bio")
    @Consumes(MediaType.TEXT_PLAIN)
    public void updateBio(String bio) {
        User user = getUserFromSession();

        userService.updateBio(user, bio);
    }

    @PUT
    @Path("{location")
    @Consumes(MediaType.TEXT_PLAIN)
    public void updateLocation(String location) {
        User user = getUserFromSession();

        userService.updateLocation(user, location);
    }


    @GET
    @Path("demoAdd")
    @Produces(MediaType.TEXT_PLAIN)
    public String demoAdd() {
        User u = new User("JohnDoe", "password", Role.User, "John Doe", "", "", "", "");
        User u2 = new User("AliceWonderland", "password", Role.User, "Alice W.", "", "I'm Alice!", "Wonderland", "");
        u = userService.createUser(u);
        u2 = userService.createUser(u2);

        Kweet k1 = kweetService.createKweet(u, "1 This is a test kweet by " + u.getDisplayname());
        Kweet k2 = kweetService.createKweet(u, "2 This is another test kweet by " + u.getDisplayname());
        Kweet k3 = kweetService.createKweet(u2, "3 Hello I'm Alice");
        Kweet k4 = kweetService.createKweet(u2, "4 How are you doing?");

        userService.followUser(u, u2.getUsername());

        kweetService.likeKweet(u, k1.getId());
        kweetService.likeKweet(u2, k1.getId());

        return "ok";
    }

    private User getUserFromSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Object user = externalContext.getSessionMap().get("user");

        if (user == null || !(user instanceof User)) {
            return null;
        }

        return (User) user;
    }
}
