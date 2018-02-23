package service;

import com.google.gson.Gson;
import dao.JPA;
import dao.UserDao;
import domain.Role;
import domain.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("users")
public class UserService extends Application {

    @Inject @JPA
    private UserDao userDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public List<User> getUsers() {
        final User a = new User("a", "123", Role.User, "a", "123.jpg", "fsdfsd", "Uden", "123.nl");
        final User b = new User("b", "321", Role.User, "b", "321.jpg", "aaaaa", "Eindhoven", "123.nl");
        List<User> users = new ArrayList<User>() {{ add(a); add(b); }};

        return users;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create")
    public List<User> create() {
        User a = new User("a", "123", Role.User, "a", "123.jpg", "fsdfsd", "Uden", "123.nl");
        userDao.createUser(a);

        return userDao.getAllUsers();
    }

    @GET
    @Produces("application/json")
    @Path("{id}")
    public Response getUser(@PathParam("id") int id) {
        //User user = entityManager.find(User.class, id);
        User a = new User("a", "123", Role.User, "a", "123.jpg", "fsdfsd", "Uden", "123.nl");
        a.setId(id);
        Gson gson = new Gson();
        return Response.ok(gson.toJson(a), MediaType.APPLICATION_JSON).build();
    }
}
