package domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class KweetTest {

    public KweetTest() {
    }

    private User userA = new User("Alice", "password", new ArrayList<Role>(){{add(Role.User); }}, "Alice", "", "", "", "") {{ setId(1); }};
    private User userB = new User("John", "password", new ArrayList<Role>(){{add(Role.User); }},"John Doe", "", "", "", "") {{ setId(2); }};

    @Test
    public void dummyTest() {
        assertEquals(true, true);
    }

    @Test
    public void hashtagTest() {
        Kweet kweet = new Kweet(null, "this is a #message with two #hashtags");
        List<String> hashtags = kweet.getHashtags();
        List<String> expected = Arrays.asList("message", "hashtags");

        assertEquals(expected, hashtags);
    }

    @Test
    public void likeTest() {
        // Set up empty kweet
        Kweet k = new Kweet(userA, "Test");

        // List of likes should be empty
        assertTrue(k.getLikes().isEmpty());

        // Add like, check if amount of likes increased and check if user is in the list
        k.addLike(userA);
        assertEquals(1, k.getLikes().size());
        assertTrue(k.getLikes().contains(userA));
    }

    @Test(expected = IllegalArgumentException.class)
    public void likeTwiceTest() {
        // Set up empty kweet
        Kweet k = new Kweet(userA, "Test");

        // Add like twice, should throw IllegalArgumentException
        k.addLike(userA);
        k.addLike(userA);
    }

    @Test
    public void unlikeTest() {
        // Set up kweet with 1 like
        Kweet k = new Kweet(userA, "Test");
        k.addLike(userA);

        assertTrue(k.getLikes().contains(userA));

        // Unlike the kweet
        k.removeLike(userA);
        assertFalse(k.getLikes().contains(userA));
    }

    @Test(expected = IllegalArgumentException.class)
    public void unlikeWithoutLikeTest() {
        // Set up empty kweet
        Kweet k = new Kweet(userA, "Test");

        // Attempt to unlike it despite having never liked it
        // should throw IllegalArgumentException
        k.removeLike(userA);
    }
}
