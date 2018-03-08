package controller;

import domain.Kweet;
import domain.User;
import service.KweetService;

import javax.ejb.Stateless;
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

        List<Kweet> kweets = kweetService.getKweets();
        return kweets;
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
        return kweetService.createKweet(k.getUser(), k.getMessage());
    }

    @DELETE
    @Path("/{id}")
    public void deleteKweet(@PathParam("id") int id) {
        kweetService.deleteKweet(id);
    }
}
