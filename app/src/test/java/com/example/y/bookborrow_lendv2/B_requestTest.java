package com.example.y.bookborrow_lendv2;

import android.graphics.Bitmap;

import org.junit.Test;

import java.util.ArrayList;


import static org.junit.Assert.assertEquals;


import static org.junit.Assert.*;



public class B_requestTest {

    @Test
    public void set_getUserName() {
        B_request br = new B_request("","","");
        br.setUserName("testName");
        assertEquals("testName",br.getUserName());
    }

    @Test
    public void set_getRating() {
        B_request br = new B_request("","","");
        br.setRating("testRating");
        assertEquals("testRating",br.getRating());
    }

    @Test
    public void set_getUserID() {
        B_request br = new B_request("","","");
        br.setUserID("testID");
        assertEquals("testID",br.getUserID());
    }

    @Test
    public void set_getPhoto() {
        B_request br = new B_request("","","");
        Bitmap photo = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888);
        br.setPhoto(photo);
        assertEquals(photo,br.getPhoto());
    }
}
