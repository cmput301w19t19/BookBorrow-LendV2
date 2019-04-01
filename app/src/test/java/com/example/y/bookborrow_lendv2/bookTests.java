package com.example.y.bookborrow_lendv2;

import android.graphics.Bitmap;
import android.media.Image;

import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class bookTests {
    book book1 = new book(UUID.randomUUID(),"name","author", "ISBN",100.1, 100.2 ,"description"
            ,"title", "Paul", "ownerName", "available", "true" ,"1@ualberta.ca");


    @Test
    public void setAndGetImage(){
        book b2 = new book();
        Bitmap photo = Bitmap.createBitmap(100, 200, Bitmap.Config.ARGB_8888);
        b2.setImage(photo);
        assertEquals("photo ",photo,b2.getImage());
    }

    @Test
    public void setAndGetID(){
        book b2 = new book();
        String id = UUID.randomUUID().toString();
        b2.setID(id);
        assertEquals("ID",b2.getID(),id);
    }

    @Test
    public void setAndGetOwnerEmail(){
        book b2 = new book();
        String s = "6@ualberta.ca";
        b2.setOwnerEmail(s);
        assertEquals("ownerEmail",b2.getOwnerEmail(),s);
    }

    /*@Test
    public void setRequestedList(){
        book book2 = new book();
        ArrayList<String> bookRequests = new ArrayList<>();

        ArrayList<String> keyset = new ArrayList<String>(bookRequests.keySet());
        book2.setRequestedList(bookRequests);
        assertEquals(book2.getRequestedList(),bookRequests.keySet());

    }*/


    @Test
    public void setAndGetBorrowerID(){
       book b2 = new book();
       String s = "AksxeukvtGMLWJ6Ub6qLCYynXmA3";
       b2.setBorrowerID(s);
       assertEquals("borrowerID",b2.getBorrowerID(),s);

    }


    @Test
    public void setAndGetISBN(){
        book b2 = new book();
        String s = "1234567890";
        b2.setISBN(s);
        assertEquals("ISBN",b2.getISBN(),s);

    }

    @Test
    public void setAndGetDescription(){
        book b2 = new book();
        String s = "description";
        b2.setDescription(s);
        assertEquals("ISBN",b2.getDescription(),s);

    }


    @Test
    public void setAndGetLongitude(){
        book book2 = new book();
        book2.setLongitude(99.9);
        assertEquals(book2.getLongitude(),99.9,0.000001);
    }


    @Test
    public void setAndgetLatitude(){
        book book2 = new book();
        book2.setLatitude(99.9);
        assertEquals(book2.getLatitude(),99.9,0.000001);
    }

    @Test
    public void setAndGetOwnerID(){
        book b2 = new book();
        String s = "AksxeukvtGMLWJ6Ub6qLCYynXmA3";
        b2.setOwnerID(s);
        assertEquals(b2.getOwnerID(),s);
    }

    @Test
    public void setAndGetName(){
        book book2 = new book();
        book2.setName("ownerName");

        assertEquals(book2.getName(),"ownerName");
    }



    @Test
    public void setAndGetAuthor(){
        book book2 = new book();
        book2.setAuthor("a");

        assertEquals(book2.getAuthor(),"a");
    }


    @Test
    public void setAndGetAndAddRequestedList(){
        String request = "bookID";
        ArrayList<String> requests= new ArrayList<>();
        book book2 = new book();
        book2.setRequestedList(requests);
        book2.addRequested(request);
        assertEquals(book2.getRequestedList(),requests);
    }

}
