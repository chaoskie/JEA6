package controller;

import Exceptions.InvalidActionException;
import Exceptions.KweetNotFoundException;
import Exceptions.KweetNotValidException;
import Exceptions.UserNotFoundException;
import controller.annotation.Secured;
import domain.Kweet;
import domain.Role;
import domain.User;
import service.KweetService;
import service.MailService;
import service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
@Path("users")
public class UserController extends Application {
    @Context private HttpServletRequest servletRequest;
    @Context SecurityContext securityContext;

    @Inject
    UserService userService;

    @Inject
    KweetService kweetService;

    @Inject
    MailService mailService;

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
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(UserRegisterRequest request) {
        try {
            User u = new User(request.getUsername(), request.getPassword(), new ArrayList<Role>(){{add(Role.User); }}, request.getUsername(), "", "", "", "");
            u.setEmail(request.getEmail());
            userService.createUser(u);

            // Email user
            mailService.sendEmail(request.getEmail(), "Account successfully created", "Welcome to Kwetter " + u.getUsername() + "!");

            String token = userService.issueToken(u.getUsername());
            return Response.status(Response.Status.CREATED).entity(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }

    @POST
    @Path("{username}/follow")
    @Secured
    public Response followUser(@PathParam("username") String username) {
        try {
            User user = getUserFromToken();
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
    @Secured
    public Response unfollowUser(@PathParam("username") String username) {
        try {
            User user = getUserFromToken();
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Response updateBio(String bio) {
        try {
            User user = getUserFromToken();
            userService.updateBio(user, bio);

            user.setBio(bio);

            return Response.ok().entity(user).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }

    @PUT
    @Path("/website")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Response updateWebsite(String website) {
        try {
            User user = getUserFromToken();
            userService.updateWebsite(user, website);

            user.setWebsite(website);

            return Response.ok().entity(user).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }

    @PUT
    @Path("/displayname")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Response updateDisplayname(String displayname) {
        try {
            User user = getUserFromToken();
            userService.updateDisplayname(user, displayname);

            user.setDisplayname(displayname);

            return Response.ok().entity(user).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }

    @PUT
    @Path("/location")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Response updateLocation(String location) {
        try {
            User user = getUserFromToken();
            userService.updateLocation(user, location);

            user.setLocation(location);

            return Response.ok().entity(user).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }

    @GET
    @Path("checkClaims")
    @Produces(MediaType.TEXT_PLAIN)
    @Secured
    public String checkClaims(@Context SecurityContext securityContext) {
        String result = "";
        result += "Username: " + securityContext.getUserPrincipal().getName();
        result += "\r\nModerator: " + securityContext.isUserInRole("moderator");
        result += "\r\nAdministrator: " + securityContext.isUserInRole("administrator");

        return result;
    }

    @GET
    @Path("demoMail/{email}")
    @Produces(MediaType.TEXT_PLAIN)
    public String demoMail(@PathParam("email") String email) throws UnsupportedEncodingException, MessagingException {
        mailService.sendEmail(email, "Test Email from Kwetter", "This is a test email from Kwetter");
        return "ok";
    }

    @GET
    @Path("demoAdd")
    @Produces(MediaType.TEXT_PLAIN)
    public String demoAdd() throws UserNotFoundException, KweetNotValidException, InvalidActionException, KweetNotFoundException{
        User u = new User("JohnDoe", "password", new ArrayList<Role>(){{add(Role.User); add(Role.Moderator); }}, "John Doe", "", "", "", "");
        User u2 = new User("AliceWonderland", "password", new ArrayList<Role>(){{add(Role.User); }}, "Alice W.", "", "I'm Alice!", "Wonderland", "");
        u = userService.createUser(u);
        u2 = userService.createUser(u2);

        Kweet k1 = kweetService.createKweet(u, "1 This is a test kweet by " + u.getDisplayname());
        Kweet k2 = kweetService.createKweet(u, "2 This is another test kweet by " + u.getDisplayname());
        Kweet k3 = kweetService.createKweet(u2, "3 Hello I'm Alice");
        Kweet k4 = kweetService.createKweet(u2, "4 How are you doing?");

        k1.setDate(new Date(1));
        k2.setDate(new Date(100));
        k3.setDate(new Date(1000));
        k4.setDate(new Date(10000));

        userService.followUser(u, u2.getUsername());

        kweetService.likeKweet(u, k1.getId());
        kweetService.likeKweet(u2, k1.getId());

        return "ok";
    }

    private User getUserFromToken() {
        Principal principal = securityContext.getUserPrincipal();
        String username = principal.getName();

        return userService.getUserByName(username);
    }
}

class UserRegisterRequest implements Serializable {
    private String username;
    private String password;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
