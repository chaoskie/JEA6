package controller;

import domain.Kweet;
import domain.User;
import service.KweetService;

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


}
