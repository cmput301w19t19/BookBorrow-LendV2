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

    //@Test
    //public void setAndGetLongitude

    /*

    @Test
    public void  setISBN(){
        book book2 = new book();
        book2.setISBN("123aaa");

        assertEquals(book2.getISBN(),"123aaa");


    }

    @Test
    public void getDescription(){


        assertEquals(book1.getDescription(),"description");

    }


    @Test
    public void setDescription(){
        book book2 = new book();
        book2.setDescription("des");

        assertEquals(book2.getDescription(),"des");

    }


    @Test
    public void getLongitude(){



        assertEquals(book1.getLongitude(),(float)100.1,0.0001);
    }


    @Test
    public void setLongitude(){
        book book2 = new book();
        book2.setLongitude(99.9);

        assertEquals(book2.getLongitude(),99.9,0.000001);


    }


    @Test
    public void getLatitude(){


        assertEquals(book1.getLatitude(),100.2,0.000001);
    }


    @Test
    public void setLatitude(){
        book book2 = new book();
        book2.setLatitude(99.9);

        assertEquals(book2.getLatitude(),99.9,0.000001);


    }

    @Test
    public void getOwnerID(){
        assertEquals(book1.getOwnerID(),"ownerName");
    }

    @Test
    public void setOwnerName(){
        book book2 = new book();
        book2.setOwnerName("ownerName");

        assertEquals(book2.getOwnerName(),"ownerName");
    }



    @Test
    public void getAuthor(){

        assertEquals(book1.getAuthor(),"author");
    }

    @Test
    public void setAuthor(){
        book book2 = new book();
        book2.setAuthor("a");

        assertEquals(book2.getAuthor(),"a");
    }

    @Test
    public void getPhoto(){

        assertEquals(book1.getPhoto(),null);
    }


    @Test
    public void setPhoto(){
        Image image1 = null;
        book book2 = new book();
        book2.setPhoto(image1);

        assertEquals(book2.getPhoto(),image1);

    }

    @Test
    public void getRequestedList(){
        borrower borrower1 = new borrower();
        ArrayList<String> requests= new ArrayList<>();
        book book2 = new book();
        book2.addRequested(borrower1.getName());
        book2.setRequestedList(requests);

        assertEquals(book2.getRequestedList(),requests);


    }

    @Test
    public void setName(){
        book book1 = new book();
        book1.setName("hello");
        assertEquals(book1.getName(),"hello");
    }


    @Test
    public void addRequested(){
        borrower borrower1 = new borrower();
        book book2 = new book();
        book2.addRequested(borrower1.getName());

        assertFalse(book2.getRequestedList().isEmpty());
    }


*/

}
