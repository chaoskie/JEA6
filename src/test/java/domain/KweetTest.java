package domain;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class KweetTest {

    public KweetTest() {
    }

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
}
