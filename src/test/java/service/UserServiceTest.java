///*
//package service;
//
//import java.util.Map;
//import java.util.Set;
//import javax.ejb.embeddable.EJBContainer;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//public class UserServiceTest {
//    
//    public UserServiceTest() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of getClasses method, of class UserService.
//     */
//    @Test
//    public void testGetClasses() throws Exception {
//        System.out.println("getClasses");
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        UserService instance = (UserService)container.getContext().lookup("java:global/classes/UserService");
//        Set<Class<?>> expResult = null;
//        Set<Class<?>> result = instance.getClasses();
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getSingletons method, of class UserService.
//     */
//    @Test
//    public void testGetSingletons() throws Exception {
//        System.out.println("getSingletons");
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        UserService instance = (UserService)container.getContext().lookup("java:global/classes/UserService");
//        Set<Object> expResult = null;
//        Set<Object> result = instance.getSingletons();
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getProperties method, of class UserService.
//     */
//    @Test
//    public void testGetProperties() throws Exception {
//        System.out.println("getProperties");
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        UserService instance = (UserService)container.getContext().lookup("java:global/classes/UserService");
//        Map<String, Object> expResult = null;
//        Map<String, Object> result = instance.getProperties();
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of login method, of class UserService.
//     */
//    @Test
//    public void testLogin() throws Exception {
//        System.out.println("login");
//        String username = "";
//        String password = "";
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        UserService instance = (UserService)container.getContext().lookup("java:global/classes/UserService");
//        String expResult = "";
//        String result = instance.login(username, password);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getTimeline method, of class UserService.
//     */
//    @Test
//    public void testGetTimeline() throws Exception {
//        System.out.println("getTimeline");
//        String idString = "";
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        UserService instance = (UserService)container.getContext().lookup("java:global/classes/UserService");
//        String expResult = "";
//        String result = instance.getTimeline(idString);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of followUser method, of class UserService.
//     */
//    @Test
//    public void testFollowUser() throws Exception {
//        System.out.println("followUser");
//        String selfIdString = "";
//        String otherIdString = "";
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        UserService instance = (UserService)container.getContext().lookup("java:global/classes/UserService");
//        boolean expResult = false;
//        boolean result = instance.followUser(selfIdString, otherIdString);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of unfollowUser method, of class UserService.
//     */
//    @Test
//    public void testUnfollowUser() throws Exception {
//        System.out.println("unfollowUser");
//        String selfIdString = "";
//        String otherIdString = "";
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        UserService instance = (UserService)container.getContext().lookup("java:global/classes/UserService");
//        boolean expResult = false;
//        boolean result = instance.unfollowUser(selfIdString, otherIdString);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getUser method, of class UserService.
//     */
//    @Test
//    public void testGetUser() throws Exception {
//        System.out.println("getUser");
//        String userIdString = "";
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        UserService instance = (UserService)container.getContext().lookup("java:global/classes/UserService");
//        String expResult = "";
//        String result = instance.getUser(userIdString);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of demoAdd method, of class UserService.
//     */
//    @Test
//    public void testDemoAdd() throws Exception {
//        System.out.println("demoAdd");
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        UserService instance = (UserService)container.getContext().lookup("java:global/classes/UserService");
//        boolean expResult = false;
//        boolean result = instance.demoAdd();
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    
//}
