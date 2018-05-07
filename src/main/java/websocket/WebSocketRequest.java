package websocket;

import domain.Kweet;

import java.io.Serializable;

public class WebSocketRequest implements Serializable {
    private String type;
    private Kweet kweet;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Kweet getKweet() {
        return kweet;
    }

    public void setKweet(Kweet kweet) {
        this.kweet = kweet;
    }
}
