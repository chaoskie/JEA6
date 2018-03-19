package dao;

import domain.Kweet;
import domain.User;

import java.util.List;

public interface KweetDao {
    public List<Kweet> getKweets();

    public List<Kweet> searchKweets(String content);

    public Kweet getKweetById(int id);

    public List<Kweet> getKweetsByUser(User user);

    public List<Kweet> getTimeline(User user);

    public Kweet createKweet(Kweet kweet);

    public void deleteKweet(Kweet k);

    public int likeKweet(Kweet k, User u);

    public int unlikeKweet(Kweet k, User u);
}
