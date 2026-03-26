package com.hustoj.service;

import com.hustoj.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;
    private JdbcTemplate jdbc;

    @BeforeEach
    void setUp() {
        jdbc = MySqlTestUtil.createJdbc();
        MySqlTestUtil.cleanTables(jdbc, "users", "loginlog", "privilege");
        userService = new UserService(jdbc);
    }

    @AfterEach
    void tearDown() {
        // Tables already cleaned before each test
    }

    @Test
    void testRegisterAndFindById() {
        int result = userService.register("testuser", "password123", "test@example.com", "Test Nick", "Test School", "127.0.0.1");
        assertTrue(result > 0);

        User user = userService.findById("testuser");
        assertNotNull(user);
        assertEquals("testuser", user.getUserId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Test Nick", user.getNick());
        assertEquals("Test School", user.getSchool());
        assertEquals("N", user.getDefunct());
    }

    @Test
    void testExists() {
        assertFalse(userService.exists("nonexistent"));
        userService.register("testuser", "password123", "test@example.com", "Test", "Test", "127.0.0.1");
        assertTrue(userService.exists("testuser"));
    }

    @Test
    void testEmailExists() {
        assertFalse(userService.emailExists("test@example.com"));
        userService.register("testuser", "password123", "test@example.com", "Test", "Test", "127.0.0.1");
        assertTrue(userService.emailExists("test@example.com"));
    }

    @Test
    void testDuplicateRegistration() {
        userService.register("testuser", "password123", "test@example.com", "Test", "Test", "127.0.0.1");
        int result = userService.register("testuser", "password456", "test2@example.com", "Test2", "Test2", "127.0.0.1");
        assertEquals(-1, result);
    }

    @Test
    void testValidateLogin() {
        userService.register("testuser", "password123", "test@example.com", "Test", "Test", "127.0.0.1");
        assertTrue(userService.validateLogin("testuser", "password123"));
        assertFalse(userService.validateLogin("testuser", "wrongpassword"));
        assertFalse(userService.validateLogin("nonexistent", "password"));
    }

    @Test
    void testUpdatePassword() {
        userService.register("testuser", "password123", "test@example.com", "Test", "Test", "127.0.0.1");
        userService.updatePassword("testuser", "newpassword456");
        assertTrue(userService.validateLogin("testuser", "newpassword456"));
        assertFalse(userService.validateLogin("testuser", "password123"));
    }

    @Test
    void testIncrementSubmit() {
        userService.register("testuser", "password123", "test@example.com", "Test", "Test", "127.0.0.1");
        User u = userService.findById("testuser");
        assertEquals(0, u.getSubmit());
        userService.incrementSubmit("testuser");
        u = userService.findById("testuser");
        assertEquals(1, u.getSubmit());
    }

    @Test
    void testIncrementSolved() {
        userService.register("testuser", "password123", "test@example.com", "Test", "Test", "127.0.0.1");
        User u = userService.findById("testuser");
        assertEquals(0, u.getSolved());
        userService.incrementSolved("testuser");
        u = userService.findById("testuser");
        assertEquals(1, u.getSolved());
    }

    @Test
    void testSetDefunct() {
        userService.register("testuser", "password123", "test@example.com", "Test", "Test", "127.0.0.1");
        userService.setDefunct("testuser", "Y");
        User u = userService.findById("testuser");
        assertEquals("Y", u.getDefunct());
        assertFalse(userService.validateLogin("testuser", "password123"));
    }

    @Test
    void testPrivileges() {
        userService.register("testuser", "password123", "test@example.com", "Test", "Test", "127.0.0.1");
        assertFalse(userService.hasPrivilege("testuser", "administrator"));
        userService.addPrivilege("testuser", "administrator", "true");
        assertTrue(userService.hasPrivilege("testuser", "administrator"));
        userService.deletePrivilege("testuser", "administrator");
        assertFalse(userService.hasPrivilege("testuser", "administrator"));
    }

    @Test
    void testCount() {
        assertEquals(0, userService.count(null));
        userService.register("user1", "pass", "u1@e.com", "U1", "S1", "127.0.0.1");
        userService.register("user2", "pass", "u2@e.com", "U2", "S2", "127.0.0.1");
        assertEquals(2, userService.count(null));
    }

    @Test
    void testSearch() {
        userService.register("testuser", "password123", "test@example.com", "Test Nick", "Test School", "127.0.0.1");
        List<User> results = userService.search("test", 10);
        assertFalse(results.isEmpty());
        assertEquals("testuser", results.get(0).getUserId());
    }

    @Test
    void testGetAllGroups() {
        userService.register("user1", "pass", "u1@e.com", "U1", "School1", "127.0.0.1");
        userService.register("user2", "pass", "u2@e.com", "U2", "School2", "127.0.0.1");
        // Set group_name via raw update since register uses school, not group_name
        jdbc.update("UPDATE users SET group_name='GroupA' WHERE user_id='user1'");
        jdbc.update("UPDATE users SET group_name='GroupB' WHERE user_id='user2'");
        List<String> groups = userService.getAllGroups();
        assertTrue(groups.contains("GroupA"), "Expected GroupA in groups: " + groups);
        assertTrue(groups.contains("GroupB"), "Expected GroupB in groups: " + groups);
    }
}
