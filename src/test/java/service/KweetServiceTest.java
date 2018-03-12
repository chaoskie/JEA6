package service;

import dao.KweetDao;
import dao.UserDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.persistence.NoResultException;
import javax.sql.DataSource;
import javax.ws.rs.NotFoundException;
import Exceptions.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
import static org.mockito.Matchers.anyList;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;


@RunWith(MockitoJUnitRunner.class)
public class KweetServiceTest {

    @Test
    public void dummyKweetServiceTest() {
        assertEquals(true, true);
    }

    @Test
    public void getKweetsTest(){
        when(kweetDao.getKweets()).thenReturn(kweets);
        assertEquals(kweetService.getKweets(), kweets);
    }

    @Test
    public void searchKweetsTest(){
        when(kweetDao.searchKweets(anyString())).thenAnswer((Answer<List<Kweet>>) invocation -> {
            Object[] args = invocation.getArguments();
            String content = args[0].toString();
            List<Kweet> result = new ArrayList<>();
            for (Object k : kweets) {
                if (((Kweet)k).getMessage().contains(content)) {
                    result.add((Kweet)k);
                }
            }
            return result;
        });
        //The content to be searched for
        String content = "Kweetykweet";
        //Use the service
        List<Kweet> result = kweetService.searchKweets(content);
        List<Kweet> check = new ArrayList<>();
        for (Kweet k : kweets) {
            if (k.getMessage().contains(content)) {
                check.add(k);
            }
        }
        assertEquals(check,result);

        //The content to be searched for
        content = "nothing";
        //Use the service
        result = kweetService.searchKweets(content);
        //list should be returned empty
        assertEquals(result, new ArrayList<Kweet>());
    }

    @Test
    public void getKweetsByUserTest(){
        searchDAOs();
        List<Kweet> result = new ArrayList<>();
        try {
            result = kweetService.getKweetsByUser(userA.getUsername());
        }
        catch(Exception e){
            fail("woops this shouldn't happen: "+ e);
        }
        List<Kweet> check = new ArrayList<>();
        for (Kweet k : kweets) {
            if (k.getUser().equals(userA)) {
                check.add(k);
            }
        }
        assertEquals(check, result);
        try {
            result = kweetService.getKweetsByUser(userC.getUsername());
        }
        catch(Exception e){
            fail("woops this shouldn't happen: "+ e);
        }
        //list should not be returned empty
        assertEquals(result.size(), 2);
    }

    @Test(expected = UserNotFoundException.class)
    public void getKweetsbyUserTest2(){
        when(userDao.getUserByName(anyString())).thenReturn(null);
        kweetService.getKweetsByUser("Jantje");
        //list should be returned empty
       // assertEquals(result.size(), 0);
    }

    @Test
    public void getTimelineTest(){
        searchDAOs();

        List<Kweet> result =  kweetService.getKweetsByUser(userC.getUsername());
        List<Kweet> check = new ArrayList<>();
        for (Kweet k : kweets) {
            if (k.getUser().equals(userC)) {
                check.add(k);
            }
        }
        assertEquals(check, result);
    }

    private void searchDAOs(){
        when(userDao.getUserByName(anyString())).thenAnswer((Answer<User>) invocation -> {
            Object[] args = invocation.getArguments();
            String name = args[0].toString();
            for(User u: users){
                if(u.getUsername().equals(name)){
                    return u;
                }
            }
            return null;
        });
        when(kweetDao.getKweetsByUser(anyObject())).thenAnswer((Answer<List<Kweet>>) invocation -> {
            Object[] args = invocation.getArguments();
            User user = (User)args[0];
            List<Kweet> result = new ArrayList<>();
            if(user==null){
                return result;
            }
            for (Object k : kweets) {
                if (((Kweet)k).getUser().equals(user)) {
                    result.add((Kweet)k);
                }
            }
            return result;
        });
    }

    @Test
    public void createKweetTest(){
        String toLong = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String exactLength = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String content =   "aaaaaaaaaa";
        Kweet testKweet = new Kweet(userC, content);

        when(userDao.getUser(anyInt())).thenAnswer((Answer<User>) invocation -> {
           Object[] args = invocation.getArguments();
           if((int)args[0] < 100){
               return null;
           }
           return userC;

        });
        when(kweetDao.createKweet(anyObject())).thenReturn(testKweet);

        assertEquals(kweetService.createKweet(userC, content), testKweet);
        assertNotEquals(kweetService.createKweet(userA, content), testKweet);
    }

    @Test(expected = NotFoundException.class)
    public void createKweetTest2(){
        User x = null;
        when(userDao.getUser(anyInt())).thenReturn(x);
        when(kweetDao.createKweet(anyObject())).thenReturn(kweetB);
        kweetService.createKweet(userA, "testshouldfail");

    }


    @Test
    public void deleteKweetTest(){

    }

    @Test
    public void likeKweetTest(){

    }

    @Test
    public void unlikeKweetTest(){

    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Mock
    private KweetDao kweetDao;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private KweetService kweetService;

    //test properties
    List<Kweet> kweets;
    Kweet kweetA;
    Kweet kweetB;
    Kweet kweetC;
    Kweet kweetD;
    List<User> users;
    User userA;
    User userB;
    User userC;

    @Before
    public void setup() {
        createItems();
        MockitoAnnotations.initMocks(this);
        //mockKweetDao = mock(KweetDao.class);
        //mockUserDao = mock(UserDao.class);
        //
        //when(mockKweetService.getKweets()).thenReturn(kweets);
}


 private void createItems(){
     // Create Users and the list of Users
     users = new ArrayList<>();
     String photo = "http://photo.com/test.jpg";
     try {
         userA = new User("Janneman", "GekkeOudeBeer", Role.Administrator, "Robinson",  photo, "Oohh Pooh", "Treehouse", "Robin.son");
         userA.setId(111);
         userB = new User("Pooh", "Honing", Role.User, "Winnie the Pooh",  photo, "I love Hunny", "Robinson's room", "Hunny.xxx");
         userB.setId(222);
         userC = new User("Tijgetje", "Stuiterstaart", Role.User, "Tijgetje Stuiterstaart",  photo, "Boink Boink", "Honderdbunderbos", "boink.com" );
         userC.setId(333);
     }
     catch(Exception ex){
         fail(ex.getMessage());
     }
     users.add(userA);
     users.add(userB);
     users.add(userC);

     // Create Kweets and the Kweets list
     kweets = new ArrayList<>();
     String content = "Kweetykweet";
     try {
         kweetA = new Kweet(userA, content);
         kweetA.setId(100);
         kweetB = new Kweet(userB, content);
         kweetB.setId(200);
         kweetC = new Kweet(userC, content);
         kweetC.setId(300);
         kweetD = new Kweet(userC, "Tweet");
         kweetD.setId(400);
     } catch (Exception ex) {
         fail(ex.getMessage());
     }
     kweets.add(kweetA);
     kweets.add(kweetB);
     kweets.add(kweetC);
     kweets.add(kweetD);
    }

}



/*//Currently commented out due to restructuring.

    @InjectMocks
    KweetDao mockKweetDao;

    @InjectMocks
    UserDao mockUserDao;

    @Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConn;
    @Mock
    PreparedStatement mockPreparedStmnt;
    @Mock
    ResultSet mockResultSet;
    int KweetId = 100;

    public KweetServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @InjectMocks
    KweetService mockKweetService;

    List<Kweet> kweets;
    Kweet kweetA;
    Kweet kweetB;
    Kweet kweetC;

    List<User> users;
    User userA;
    User userB;
    User userC;

    @Before
    public void setUp() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockDataSource.getConnection(anyString(), anyString())).thenReturn(mockConn);
        doNothing().when(mockConn).commit();
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStmnt);
        doNothing().when(mockPreparedStmnt).setString(anyInt(), anyString());
        when(mockPreparedStmnt.execute()).thenReturn(Boolean.TRUE);
        when(mockPreparedStmnt.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockKweetService.getKweets()).thenReturn(kweets);
        when(mockKweetService.getKweets()).thenReturn(kweets);
        when(mockKweetService.searchKweets(anyString())).thenAnswer(new Answer<List<Kweet>>(){
            @Override
            public List<Kweet> answer(InvocationOnMock invocation) throws Throwable{
                Object[] args = invocation.getArguments();
                String content = args[0].toString();
                List<Kweet> result = new ArrayList<>();
                for (Kweet k : kweets) {
                    if (k.getMessage().contains(content)) {
                        result.add(k);
                    }
                }
                return result;
            }
        });

        when(mockKweetService.getKweetsByUser(anyString())).thenAnswer(new Answer<List<Kweet>>(){
            @Override
            public List<Kweet> answer(InvocationOnMock invocation) throws Throwable{
                Object[] args = invocation.getArguments();
                String content = args[0].toString();
                List<Kweet> result = new ArrayList<>();
                for (Kweet k : kweets) {
                    if (k.getUser().equals(content)) {
                        result.add(k);
                    }
                }
                return result;
            }
        });

        // Create Kweets and the Kweets list
        kweets = new ArrayList<>();
        String content = "Kweetykweet";
        Date date = new Date();
        try {
            kweetA = new Kweet(content, date, 1);
            kweetA.setId(100);
            kweetA.setUser(userA);
            kweetB = new Kweet(content, date, 2);
            kweetB.setId(200);
            kweetB.setUser(userB);
            kweetC = new Kweet(content, date, 3);
            kweetC.setId(300);
            kweetC.setUser(userC);
        }
        catch(Exception ex){
            fail(ex.getMessage());
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
            userA.setId(111);
            userB = new User(photo, name, password, bio, location, website, User);
            userB.setId(222);
            userC = new User(photo, name, password, bio, location, website, User);
            userC.setId(333);
        }
        catch(Exception ex){
            fail(ex.getMessage());
        }
        users.add(userA);
        users.add(userB);
        users.add(userC);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getKweets method, of class KweetService
     */
   /* @Test
    public void testGetKweets() throws Exception{
        assertEquals(mockKweetService.getKweets(), kweets);
    }

    /**
     * Test of searchKweets method, of class KweetService.
     */
  /*  @Test
    public void testSearchKweets() throws Exception {
        //The content to be searched for
        String content = "Kweetykweet";
        //Use the service
        List<Kweet> result = mockKweetService.searchKweets(content);
        int index = 0;
        for (Kweet k : kweets) {
            if (k.getMessage().contains(content)) {
               assertEquals(k, result.get(index));
            }
            index++;
        }
    }

    /**
     * Test of getKweets by users method, of class KweetService.
     */
 /*   @Test
    public void testGetKweetsbyUser() throws Exception {
        //Use the service
        List<Kweet> result = mockKweetService.getKweetsByUser(userA.getUsername());
        int index = 0;
        for (Kweet k : kweets) {
            if (k.getUser().equals(userA.getUsername())) {
                assertEquals(k, result.get(index));
            }
            index++;
        }
    }
}
*/



