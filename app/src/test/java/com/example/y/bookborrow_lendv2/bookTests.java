package com.example.y.bookborrow_lendv2;

import android.media.Image;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class bookTests {

    @Test
    public void getBookRatinf(){
        book book1 = new book("name","author", "ISBN",(float)100.1, (float)100.2 ,"description"
                ,"title", (float)2.5, "borrowerName", "ownerName", "status" );

        assertEquals(book1.getBookRating(),(float)2.5,0.00001);
    }



    @Test
    public void setBookRating(){
        book book1 = new book();
        book1.setBookRating((float)1.1);
        assertEquals(book1.getBookRating(),(float)1.1,0.00001);


    }

    @Test
    public void setRequestedList(){
        book book1 = new book();
        ArrayList<borrower> bookRequests = new ArrayList<borrower>();
        book1.setRequestedList(bookRequests);
        assertEquals(book1.getRequestedList(),bookRequests);

    }

    @Test
    public void getBorrowerName(){
        book book1 = new book("name","author", "ISBN",(float)100.1, (float)100.2 ,"description"
                ,"title", (float)2.5, "borrowerName", "ownerName", "status" );

        assertEquals(book1.getBorrowerName(),"borrowerName");

    }



    @Test
    public void setBorrowerName(){
        borrower borrower1= new borrower();
        borrower1.setName("Paul");
        book book1 = new book();
        book1.setBorrowerName("Paul");

        assertEquals(book1.getBorrowerName(),"Paul");

    }


    @Test
    public void getISBN(){
        book book1 = new book("name","author", "ISBN",(float)100.1, (float)100.2 ,"description"
                ,"title", (float)2.5, "borrowerName", "ownerName", "status" );

        assertEquals(book1.getISBN(),"ISBN");

    }

    @Test
    public void  setISBN(){
        book book1 = new book();
        book1.setISBN("123aaa");

        assertEquals(book1.getISBN(),"123aaa");


    }

    @Test
    public void getDescription(){
        book book1 = new book("name","author", "ISBN",(float)100.1, (float)100.2 ,"description"
                ,"title", (float)2.5, "borrowerName", "ownerName", "status" );
        assertEquals(book1.getDescription(),"description");

    }


    @Test
    public void setDescription(){
        book book1 = new book();
        book1.setDescription("des");

        assertEquals(book1.getDescription(),"des");

    }


    @Test
    public void getLongitude(){
        book book1 = new book("name","author", "ISBN",(float)100.1, (float)100.2 ,"description"
                ,"title", (float)2.5, "borrowerName", "ownerName", "status" );

        assertEquals(book1.getLongitude(),(float)100.1,0.0001);
    }


    @Test
    public void setLongitude(){
        book book1 = new book();
        book1.setLongitude((float)99.9);

        assertEquals(book1.getLongitude(),(float)99.9,0.000001);


    }


    @Test
    public void getLatitude(){
        book book1 = new book("name","author", "ISBN",(float)100.1, (float)100.2 ,"description"
                ,"title", (float)2.5, "borrowerName", "ownerName", "status" );

        assertEquals(book1.getLatitude(),(float)100.2,0.000001);
    }


    @Test
    public void setLatitude(){
        book book1 = new book();
        book1.setLatitude((float)99.9);

        assertEquals(book1.getLatitude(),(float)99.9,0.000001);


    }

    @Test
    public void getOwnerName(){
        book book1 = new book("name","author", "ISBN",(float)100.1, (float)100.2 ,"description"
                ,"title", (float)2.5, "borrowerName", "ownerName", "status" );

        assertEquals(book1.getOwnerName(),"ownerName");
    }

    @Test
    public void setOwnerName(){
        book book1 = new book();
        book1.setOwnerName("ownerName");

        assertEquals(book1.getOwnerName(),"ownerName");
    }

    @Test
    public void getTitle(){
        book book1 = new book("name","author", "ISBN",(float)100.1, (float)100.2 ,"description"
                ,"title", (float)2.5, "borrowerName", "ownerName", "status" );

        assertEquals(book1.getTitle(),"title");
    }

    @Test
    public void setTitle(){
        book book1 = new book();
        book1.setTitle("Title");

        assertEquals(book1.getTitle(),"Title");
    }

    @Test
    public void getStatus(){
        book book1 = new book("name","author", "ISBN",(float)100.1, (float)100.2 ,"description"
                ,"title", (float)2.5, "borrowerName", "ownerName", "status" );

        assertEquals(book1.getStatus(),"status");
    }

    @Test
    public void setStatus(){
        book book1 = new book();
        book1.setStatus("s");

        assertEquals(book1.getStatus(),"s");
    }


    @Test
    public void getAuthor(){
        book book1 = new book("name","author", "ISBN",(float)100.1, (float)100.2 ,"description"
                ,"title", (float)2.5, "borrowerName", "ownerName", "status" );

        assertEquals(book1.getAuthor(),"author");
    }

    @Test
    public void setAuthor(){
        book book1 = new book();
        book1.setAuthor("a");

        assertEquals(book1.getAuthor(),"a");
    }




}
