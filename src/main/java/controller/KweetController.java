package controller;

import domain.Kweet;
import domain.User;
import service.KweetService;

import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("kweets")
public class KweetController extends Application {

    @Inject
    KweetService kweetService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Kweet> getKweets() {
        return kweetService.getKweets();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{username}")
    public List<Kweet> getKweets(@PathParam("username") String username) {
        return kweetService.getKweetsByUser(username);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{username}/timeline")
    public List<Kweet> getTimeline(@PathParam("username") String username) {
        return kweetService.getTimeline(username);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Kweet createKweet(Kweet k) {
        User user = getUserFromSession();

        return kweetService.createKweet(user, k.getMessage());
    }

    @DELETE
    @Path("/{id}")
    public void deleteKweet(@PathParam("id") int id) {
        User user = getUserFromSession();

        kweetService.deleteKweet(user, id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id}/like")
    public int likeKweet(@PathParam("id") int id) {
        User user = getUserFromSession();

        return kweetService.likeKweet(user, id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id}/unlike")
    public int unlikeKweet(@PathParam("id") int id) {
        User user = getUserFromSession();

        return kweetService.unlikeKweet(user, id);
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
