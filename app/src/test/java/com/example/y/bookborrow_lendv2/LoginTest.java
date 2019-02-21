package com.example.y.bookborrow_lendv2;

import static org.junit.Assert.*;
import org.junit.Test;

public class LoginTest {
    public String test_username= "username";
    public String test_passwords= "12344565";

    public LoginTest(){}

    @Test
    public void testuserName(){
        lender lender1= new lender();
        assertEquals(lender1.getName(),test_username);
    }

    @Test
    public void testgetPasswords(){
        lender lender1= new lender();
        assertEquals(lender1.getPassword(),test_username);
    }

}