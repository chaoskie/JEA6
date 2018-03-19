package controller;

import domain.Kweet;
import domain.User;
import service.KweetService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Stateless
@Path("kweets")
public class KweetController extends Application {
    @Context private HttpServletRequest servletRequest;

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
    public Response getKweets(@PathParam("username") String username) {
        try {
            return Response.status(Response.Status.OK).entity(new GenericEntity<List<Kweet>>(kweetService.getKweetsByUser(username)) {}).build();
        }  catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{username}/timeline")
    public Response getTimeline(@PathParam("username") String username) {
        try {
            return Response.status(Response.Status.OK).entity(new GenericEntity<List<Kweet>>(kweetService.getTimeline(username)) {}).build();
        }  catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createKweet(Kweet k) {
        try {
            User user = getUserFromSession();
            Kweet result =  kweetService.createKweet(user, k.getMessage());

            return Response.status(Response.Status.CREATED).entity(result).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteKweet(@PathParam("id") int id) {
        try {
            User user = getUserFromSession();
            kweetService.deleteKweet(user, id);

            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (NotAuthorizedException e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id}/like")
    public Response likeKweet(@PathParam("id") int id) {
        try {
            User user = getUserFromSession();
            int count =  kweetService.likeKweet(user, id);

            return Response.status(Response.Status.OK).entity(count).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id}/unlike")
    public Response unlikeKweet(@PathParam("id") int id) {
        try {
            User user = getUserFromSession();
            int count =  kweetService.unlikeKweet(user, id);

            return Response.status(Response.Status.OK).entity(count).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong").build();
        }
    }

    private User getUserFromSession() {
        Object user = servletRequest.getSession().getAttribute("user");

        if (user == null || !(user instanceof User)) {
            return null;
        }

        return (User) user;
    }
}
