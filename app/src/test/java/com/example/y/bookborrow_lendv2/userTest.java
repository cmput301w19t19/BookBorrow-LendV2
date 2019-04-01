package com.example.y.bookborrow_lendv2;

import android.graphics.Bitmap;
import android.media.Image;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * since user is an abstract class, we create another class "NormalUser" which extends user class
 * and we are going to write unit tests for methods in NormalUser
 */

public class userTest {

    NormalUser user1 = new NormalUser("name", null, "password","12345", "email@some.com");

    @Test
    public void setAndGetName(){
        NormalUser user2 = new NormalUser();
        user2.setName("testName");
        assertEquals(user2.getName(),"testName");
    }

    @Test
    public void setAndGetPhoto(){
        NormalUser user2 = new NormalUser();
        Bitmap photo = Bitmap.createBitmap(100, 200, Bitmap.Config.ARGB_8888);
        user2.setPhoto(photo);
        assertEquals("photo ",photo,user2.getPhoto());

    }

    @Test
    public void setAndGetPhone(){
        NormalUser user2 = new NormalUser();
        user2.setPhone("123456");
        assertEquals(user2.getPhone(),"123456");
    }

    @Test
    public void setAndGetPassword(){
        NormalUser user2 = new NormalUser();
        user2.setPassword("123aaa");
        assertEquals(user2.getPassword(),"123aaa");
    }

    @Test
    public void setAndGetEmail(){
        NormalUser user2 = new NormalUser();
        user2.setEmail("aaa@ualberta.ca");
        assertEquals(user2.getEmail(),"aaa@ualberta.ca");
    }

    @Test
    public void setAndGetUid(){
        NormalUser user2 = new NormalUser();
        user2.setUid("userID");
        assertEquals(user2.getUid(),"userID");
    }

}
