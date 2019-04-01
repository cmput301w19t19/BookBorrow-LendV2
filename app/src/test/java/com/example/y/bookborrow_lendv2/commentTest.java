package com.example.y.bookborrow_lendv2;

import android.graphics.Bitmap;

import org.junit.Test;

import java.util.ArrayList;


import static org.junit.Assert.assertEquals;


import static org.junit.Assert.*;


public class commentTest {

    @Test
    public void set_getUserName() {
        comment comment = new comment("","","","");
        comment.setUserName("testName");
        assertEquals("testName",comment.getUserName());
    }

    @Test
    public void set_getID() {
        comment comment = new comment("","","","");
        comment.setID("testID");
        assertEquals("testID",comment.getID());
    }

    @Test
    public void set_getRating() {
        comment comment = new comment("","","testRating","");
        assertEquals("testRating",comment.getRating());
    }

    @Test
    public void set_getComment() {
        comment comment = new comment("","","","");
        comment.setComment("testComment");
        assertEquals("testComment",comment.getComment());
    }

    @Test
    public void set_getPhoto() {
        comment comment = new comment("","","","");
        Bitmap photo = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888);
        comment.setPhoto(photo);
        assertEquals(photo,comment.getPhoto());
    }
}
