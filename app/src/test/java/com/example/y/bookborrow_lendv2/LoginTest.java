package com.example.y.bookborrow_lendv2;

import static org.junit.Assert.*;
import org.junit.Test;

public class LoginTest {
    NormalUser user1 = new NormalUser("name", null, "password",(Integer)12345, "email@some.com");

    @Test
    public void getName(){
        assertEquals(user1.getName(),"name");

    }
    @Test
    public void getPassword(){
        assertEquals(user1.getPassword(),"password");

    }



}