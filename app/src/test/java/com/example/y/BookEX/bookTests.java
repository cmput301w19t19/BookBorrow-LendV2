package com.example.y.BookEX;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class bookTests {
    Book book1 = new Book("name","author", "ISBN",100.1, 100.2 ,"description"
            ,"title", 2.5, "borrowerName", "ownerName", "status" );
/*
    @Test
    public void getBookRatinf(){



        assertEquals(book1.getBookRating(),(float)2.5,0.00001);
    }



    @Test
    public void setBookRating(){
        Book book2 = new Book();
        book2.setBookRating((float)1.1);
        assertEquals(book2.getBookRating(),(float)1.1,0.00001);


    }*/

    @Test
    public void setRequestedList(){
        Book book2 = new Book();
        ArrayList<String> bookRequests = new ArrayList<>();
        book2.setRequestedList(bookRequests);
        assertEquals(book2.getRequestedList(),bookRequests);

    }

    /*@Test
    public void getBorrowerName(){


        assertEquals(book1.getBorrowerName(),"borrowerName");

    }
*/


    @Test
    public void setBorrowerName(){
        Borrower borrower1= new Borrower();
        borrower1.setName("Paul");
        Book book2 = new Book();
        book2.setBorrowerName("Paul");

        assertEquals(book2.getBorrowerName(),"Paul");

    }


    @Test
    public void getISBN(){



        assertEquals(book1.getISBN(),"ISBN");

    }

    @Test
    public void  setISBN(){
        Book book2 = new Book();
        book2.setISBN("123aaa");

        assertEquals(book2.getISBN(),"123aaa");


    }

  /*  @Test
    public void getDescription(){


        assertEquals(book1.getDescription(),"description");

    }
*/

    @Test
    public void setDescription(){
        Book book2 = new Book();
        book2.setDescription("des");

        assertEquals(book2.getDescription(),"des");

    }


 /*   @Test
    public void getLongitude(){



        assertEquals(book1.getLongitude(),(float)100.1,0.0001);
    }

*/
    @Test
    public void setLongitude(){
        Book book2 = new Book();
        book2.setLongitude(99.9);

        assertEquals(book2.getLongitude(),99.9,0.000001);


    }

/*
    @Test
    public void getLatitude(){


        assertEquals(book1.getLatitude(),100.2,0.000001);
    }
*/

    @Test
    public void setLatitude(){
        Book book2 = new Book();
        book2.setLatitude(99.9);

        assertEquals(book2.getLatitude(),99.9,0.000001);


    }
/*
    @Test
    public void getOwnerName(){


        assertEquals(book1.getOwnerName(),"ownerName");
    }
*/
    @Test
    public void setOwnerName(){
        Book book2 = new Book();
        book2.setOwnerName("ownerName");

        assertEquals(book2.getOwnerName(),"ownerName");
    }



    @Test
    public void getAuthor(){

        assertEquals(book1.getAuthor(),"author");
    }

    @Test
    public void setAuthor(){
        Book book2 = new Book();
        book2.setAuthor("a");

        assertEquals(book2.getAuthor(),"a");
    }
/*
    @Test
    public void getPhoto(){

        assertEquals(book1.getPhoto(),null);
    }
*/
/*
    @Test
    public void setPhoto(){
        Image image1 = null;
        Book book2 = new Book();
        book2.setPhoto(image1);

        assertEquals(book2.getPhoto(),image1);

    }
*/
    @Test
    public void getRequestedList(){
        Borrower borrower1 = new Borrower();
        ArrayList<String> requests= new ArrayList<>();
        Book book2 = new Book();
        book2.addRequested(borrower1.getName());
        book2.setRequestedList(requests);

        assertEquals(book2.getRequestedList(),requests);


    }

    @Test
    public void setName(){
        Book book1 = new Book();
        book1.setName("hello");
        assertEquals(book1.getName(),"hello");
    }


    @Test
    public void addRequested(){
        Borrower borrower1 = new Borrower();
        Book book2 = new Book();
        book2.addRequested(borrower1.getName());

        assertFalse(book2.getRequestedList().isEmpty());
    }




}
