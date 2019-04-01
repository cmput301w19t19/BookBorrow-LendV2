package com.example.y.bookborrow_lendv2;

import android.graphics.Bitmap;
import android.media.Rating;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class RatingAndCommentTest {

    @Test
    public void set_getID() {
        RatingAndComment rc = new RatingAndComment();
        rc.setID("testID");
        assertEquals("testID",rc.getID());
    }

    @Test
    public void set_getRating() {
        RatingAndComment rc = new RatingAndComment();
        rc.setRating(8.88);
        assertEquals("8.88",rc.getRating());
    }

    @Test
    public void set_getComment() {
        RatingAndComment rc = new RatingAndComment();
        rc.setComment("testComment");
        assertEquals("testComment",rc.getComment());
    }
}
