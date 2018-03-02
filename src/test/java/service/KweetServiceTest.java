package service;

import dao.KweetDao;
import dao.UserDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import domain.Kweet;
import domain.Role;
import static domain.Role.User;
import domain.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

public class KweetServiceTest {
/* Currently commented out due to restructuring.

    @Mock
    UserDAO userDAO;

    @Mock
    KweetDAO kweetDAO;

    @InjectMocks
    KweetService kweetService;

    List<Kweet> kweets;
    Kweet kweetA;
    Kweet kweetB;
    Kweet kweetC;

    List<User> users;
    User userA;
    User userB;
    User userC;

    public KweetServiceTest() {
    }

    @Before
    public void setUp() {
        kweetService = new KweetService();
        MockitoAnnotations.initMocks(this);

        // Create Kweets and the Kweets list
        kweets = new ArrayList<>();
        String content = "Kweetykweet";
        Date date = new Date();
        try {
            kweetA = new Kweet(content, date, 1);
            kweetA.setId(1L);
            kweetB = new Kweet(content, date, 2);
            kweetB.setId(2L);
            kweetC = new Kweet(content, date, 3);
            kweetC.setId(3L);
        } catch (InvalidMessageException ex) {
            fail("InvalidMessageException!");
        } catch (InvalidPostedById ex) {
            fail("InvalidPostedById!");
        }
        kweets.add(kweetA);
        kweets.add(kweetB);
        kweets.add(kweetC);

        // Create Users and the list of Users
        users = new ArrayList<>();
        String photo = "http://photo.com/test.jpg";
        String name = "The Doctor";
        String password = "gr8passwordm8";
        String bio = "This password is really secure";
        String location = "In a blue police box";
        String website = "http://doctor.who/";
        try {
            userA = new User(photo, name, password, bio, location, website, Role.Administrator);
            userA.setId(1L);
            userB = new User(photo, name, password, bio, location, website, User);
            userB.setId(2L);
            userC = new User(photo, name, password, bio, location, website, User);
            userC.setId(3L);
        } catch (InvalidPhotoException ex) {
            fail("InvalidPhotoException!");
        } catch (InvalidWebsiteException ex) {
            fail("InvalidWebsiteException");
        }
        users.add(userA);
        users.add(userB);
        users.add(userC);
    }

    /**
     * Test of searchKweets method, of class KweetService.
     */
    /*
    @Test
    public void testSearchKweets() throws Exception {
        //The content to be searched for
        String content = "Kweetykweet";
        //Set Mockito to return a value if the kweetDAO is triggered
        Mockito.when(kweetDAO.searchKweets(content)).thenReturn(kweets);
        //Use the service
        String result = kweetService.searchKweets(content);
        String expResult = UtilityService.jsonify(kweets);
        //Assert the values
        assertEquals(expResult, result);
    }
*/
}
