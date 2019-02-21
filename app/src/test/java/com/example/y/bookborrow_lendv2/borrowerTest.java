package com.example.y.bookborrow_lendv2;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class borrowerTest {





    @Test
    public void getGet_SetRating(){
        borrower borrower1 = new borrower();
        borrower1.setBorrowerRating((float)3.3);
        assertEquals((float)3.3,borrower1.getBorrowerRating(),(float)0.0001);

    }
    @Test   //failed
    public void deleteBorrowedBook(){

        borrower borrower1 = new borrower();
        book book1 = new book();
        ArrayList<book> books = new ArrayList<book>();
        books.add(book1);
        borrower1.setBorrowedBook(books);
        borrower1.deleteBorrowedBook(book1);


        assertEquals((Boolean)true,borrower1.getRequestedBookList().isEmpty());


    }











}