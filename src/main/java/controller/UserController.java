package controller;

import Exceptions.InvalidActionException;
import Exceptions.KweetNotFoundException;
import Exceptions.KweetNotValidException;
import Exceptions.UserNotFoundException;
import domain.Kweet;
import domain.Role;
import domain.User;
import service.KweetService;
import service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Date;
import java.util.List;

@Stateless
@Path("users")
public class UserController extends Application {
    @Context private HttpServletRequest servletRequest;

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
    public Response getUser(@PathParam("username") String name) {
        try {
            User user = userService.getUserByName(name);
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }

    @GET
    @Path("{username}/followers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFollowers(@PathParam("username") String name) {
        try {
            List<User> followers = userService.getFollowers(name);
            return Response.status(Response.Status.OK).entity(new GenericEntity<List<User>>(followers) {}).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }

    @GET
    @Path("{username}/following")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFollowing(@PathParam("username") String name) {
        try {
            List<User> following = userService.getFollowing(name);
            return Response.status(Response.Status.OK).entity(new GenericEntity<List<User>>(following) {}).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }

    @POST
    @Path("{username}/follow")
    public Response followUser(@PathParam("username") String username) {
        try {
            User user = getUserFromSession();
            userService.followUser(user, username);

            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }

    }

    @POST
    @Path("{username}/unfollow")
    public Response unfollowUser(@PathParam("username") String username) {
        try {
            User user = getUserFromSession();
            userService.unfollowUser(user, username);

            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }

    @PUT
    @Path("/bio")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response updateBio(String bio) {
        try {
            User user = getUserFromSession();
            userService.updateBio(user, bio);

            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }

    @PUT
    @Path("/location")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response updateLocation(String location) {
        try {
            User user = getUserFromSession();
            userService.updateLocation(user, location);

            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }


    @GET
    @Path("demoAdd")
    @Produces(MediaType.TEXT_PLAIN)
    public String demoAdd() throws UserNotFoundException, KweetNotValidException, InvalidActionException, KweetNotFoundException{
        User u = new User("JohnDoe", "password", Role.User, "John Doe", "", "", "", "");
        User u2 = new User("AliceWonderland", "password", Role.User, "Alice W.", "", "I'm Alice!", "Wonderland", "");
        u = userService.createUser(u);
        u2 = userService.createUser(u2);

        Kweet k1 = kweetService.createKweet(u, "1 This is a test kweet by " + u.getDisplayname());
        Kweet k2 = kweetService.createKweet(u, "2 This is another test kweet by " + u.getDisplayname());
        Kweet k3 = kweetService.createKweet(u2, "3 Hello I'm Alice");
        Kweet k4 = kweetService.createKweet(u2, "4 How are you doing?");

        k1.setDate(new Date(1));
        k2.setDate(new Date(100));
        k3.setDate(new Date(1000));
        k4.setDate(new Date(1));

        userService.followUser(u, u2.getUsername());

        kweetService.likeKweet(u, k1.getId());
        kweetService.likeKweet(u2, k1.getId());

        return "ok";
    }

    private User getUserFromSession() {
        Object user = servletRequest.getSession().getAttribute("user");

        if (user == null || !(user instanceof User)) {
            return null;
        }

        return (User) user;
    }
}
