package com.example.vaccme;

import junit.framework.TestCase;

public class UserTest extends TestCase {

    public void testGetUsername() {
        User user = new User("test@test.com", "test");
        assertEquals("testGetUsername: ",user.getUsername(), "test");
    }

    public void testSetUsername() {
        User user = new User("test@test.com", "test");
        user.setUsername("test2");
        assertEquals("testSetUsername: ",user.getUsername(), "test2");
    }

    public void testGetEmail() {
        User user = new User("test@test.com", "test");
        assertEquals("testGetEmail: ", user.getEmail(), "test@test.com");
    }

    public void testSetEmail() {
        User user = new User("test@test.com", "test");
        user.setEmail("test2@test.com");
        assertEquals("testSetEmail: ", user.getEmail(), "test2@test.com");
    }

    public void testIsAdmin() {
        User user = new User("test@test.com", "test");
        assertEquals("testIsAdmin: ", user.isAdmin(), false);
    }
}