package domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Kweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private User user;

    private Date date;
    private String message;

    @OneToMany
    private List<User> likes;

    protected Kweet() { }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public List<User> getLikes() {
        return likes;
    }
}
