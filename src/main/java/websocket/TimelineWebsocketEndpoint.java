package websocket;

import Exceptions.InvalidActionException;
import Exceptions.KweetNotFoundException;
import Exceptions.KweetNotValidException;
import Exceptions.UserNotFoundException;
import com.google.gson.Gson;
import domain.Kweet;
import domain.User;
import service.KweetService;
import service.UserService;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/ws/timeline")
public class TimelineWebsocketEndpoint {
    private static HashMap<Session, User> sessions = new HashMap<>();

    @Inject
    private UserService userService;

    @Inject
    private KweetService kweetService;

    @OnOpen
    public void onOpen(Session session) {
        String token = session.getQueryString();
        User user = userService.parseUserFromToken(token);
        sessions.put(session, user);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        Gson gson = new Gson();
        WebSocketRequest request = gson.fromJson(message, WebSocketRequest.class);

        try {
            switch(request.getType()) {
                case "createKweet":
                    handleKweetCreation(request.getKweet(), session);
                    break;

                case "deleteKweet":
                    handleKweetDeletion(request.getKweet(), session);
                    break;
            }
        } catch (Exception e) {
            WebSocketResponse response = new WebSocketResponse();
            response.setType("error");
            response.setErrorMessage(e.getMessage());

            session.getBasicRemote().sendText(gson.toJson(response));
        }
    }

    private void handleKweetDeletion(Kweet kweet, Session session) throws InvalidActionException, KweetNotFoundException, IOException {
        User user = sessions.get(session);
        kweetService.deleteKweet(user, kweet.getId());

        WebSocketResponse response = new WebSocketResponse();
        response.setType("deleteKweet");
        response.setKweet( kweet );
        response.setUser( user );

        Gson gson = new Gson();
        String responseJson = gson.toJson(response);

        // Send response to sender
        session.getBasicRemote().sendText(responseJson);

        // Iterate through all ongoing sessions
        for(Map.Entry<Session, User> entry : sessions.entrySet()) {
            User u = entry.getValue();

            if (u.getFollowing().contains(user)) {
                // Send response to user informing them about a kweet of a user they follow
                entry.getKey().getBasicRemote().sendText(responseJson);
            }
        }
    }

    private void handleKweetCreation(Kweet kweet, Session session) throws KweetNotValidException, UserNotFoundException, IOException {
        User user = sessions.get(session);
        Kweet createdKweet = kweetService.createKweet(user, kweet.getMessage());

        WebSocketResponse response = new WebSocketResponse();
        response.setKweet(createdKweet);
        response.setType("createKweet");

        Gson gson = new Gson();
        String responseJson = gson.toJson(response);

        // Send response to sender
        session.getBasicRemote().sendText(responseJson);

        // Iterate through all ongoing sessions
        for(Map.Entry<Session, User> entry : sessions.entrySet()) {
            User u = entry.getValue();

            if (u.getFollowing().contains(user)) {
                // Send response to user informing them about a kweet of a user they follow
                entry.getKey().getBasicRemote().sendText(responseJson);
            }
        }


    }

    @OnError
    public void onError(Throwable t) {
        System.out.println(t.getMessage());
    }
}

