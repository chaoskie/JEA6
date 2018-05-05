package controller;

import domain.Role;
import domain.User;
import io.jsonwebtoken.*;

import service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

@Path("/authentication")
public class AuthenticationController {
    @Inject
    UserService userService;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        // Authenticate the user using the credentials provided
        if (!authenticate(username, password)) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Invalid login").build();
        }

        // Issue a token for the user
        String token = userService.issueToken(username);

        // Return the token on the response
        return Response.ok(token).build();
    }

    private boolean authenticate(String username, String password) {
        return userService.login(username, password);
    }

}

class Credentials implements Serializable {
    private String username;
    private String password;

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
}
