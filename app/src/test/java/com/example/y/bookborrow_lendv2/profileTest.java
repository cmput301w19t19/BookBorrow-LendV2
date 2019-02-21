package com.example.y.bookborrow_lendv2;

import android.media.Image;
import android.provider.ContactsContract;

import org.junit.Test;
import static org.junit.Assert.*;

public class profileTest {
    public String test_username = "uname";
    public String test_phone_number= "12345678";
    public String test_email= "uname@email.com";

    public profileTest(){}

    @Test
    public void testgetName(){
        lender lender1= new lender();
        assertEquals(lender1.getName(),test_username);
    }
    @Test
    public void testsetName(){
        lender lender1= new lender();
        String new_name= "new_name";
        lender1.setName(new_name);
        assertEquals(lender1.getName(),new_name);
    }

    @Test
    public void testgetPhone(){
        lender lender1= new lender();
        assertEquals(lender1.getPhone(),test_phone_number);
    }

    @Test
    public void testsetPhone(){
        lender lender1= new lender();
        Integer new_number= 992238201;
        lender1.setPhone(new_number);
        assertEquals(lender1.getPhone(),new_number);
    }

    @Test
    public void testgetEmail(){
        lender lender1= new lender();
        assertEquals(lender1.getEmail(),test_email);
    }

    @Test
    public void testsetEmail(){
        lender lender1= new lender();
        String new_email= "hahah2GM@gmail.com";
        lender1.setEmail(new_email);
        assertEquals(lender1.getEmail(),new_email);
    }

}
