package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.*;

@Entity
public class Kweet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false, length = 140)
    private String message;

    @OneToMany(fetch = FetchType.LAZY)
    private List<User> likes;

    public Kweet() { }

    public Kweet(User user, String message) {
        this.user = user;
        this.date = new Date();
        this.message = message;
        this.likes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    public void addLike(User user) {
        if (this.likes.contains(user)) {
            throw new IllegalArgumentException("User already liked this kweet");
        }

        this.likes.add(user);
    }

    public void removeLike(User user) {
        if (!this.likes.contains(user)) {
            throw new IllegalArgumentException("User didn't like this kweet");
        }

        this.likes.remove(user);
    }

    public List<String> getHashtags() {
        List<String> hashtags = new ArrayList<>();
        String regexPattern = "(#\\w+)";

        Pattern p = Pattern.compile(regexPattern);
        Matcher m = p.matcher(this.message);
        while (m.find()) {
            String hashtag = m.group(1).replace("#", "");
            hashtags.add(hashtag);
        }

        return hashtags;
    }
}
