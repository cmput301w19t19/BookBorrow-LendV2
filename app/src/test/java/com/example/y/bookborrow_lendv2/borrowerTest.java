package com.example.y.bookborrow_lendv2;

import org.junit.Test;

import java.util.ArrayList;


import static org.junit.Assert.assertEquals;


import static org.junit.Assert.*;


public class borrowerTest {



   @Test
    public void set_getRequestedBookList() {
        Borrower borrower1 = new Borrower();
        ArrayList<Book> Books = new ArrayList<Book>();
        borrower1.setRequestedBookList(Books);
        assertEquals(Books,borrower1.getRequestedBookList());
    }

    @Test
    public void set_getBorrowedBook() {
        Borrower borrower1 = new Borrower();
        ArrayList<Book> Books = new ArrayList<Book>();
        borrower1.setBorrowedBook(Books);
        assertEquals(Books,borrower1.getBorrowedBook());
    }


    @Test
    public void addBorrowedBook() {
        Book book1 = new Book();
        Borrower borrower1 = new Borrower();

        borrower1.addBorrowedBook(book1);
        assertTrue(borrower1.getBorrowedBook().contains(book1));
    }

    @Test
    public void addRequestedBook() {
        Book book1 = new Book();
        Borrower borrower1 = new Borrower();

        borrower1.addRequestedBook(book1);

        assertTrue(borrower1.getRequestedBookList().contains(book1));
    }

    @Test
    public void deleteRequestedBook() {
        Book book1 = new Book();
        Borrower borrower1 = new Borrower();
        borrower1.addRequestedBook(book1);
        borrower1.deleteRequestedBook(book1);
        assertTrue(borrower1.getRequestedBookList().isEmpty());
    }

    @Test
    public void deleteBorrowedBook() {

        Borrower borrower1 = new Borrower();
        Book book1 = new Book();

        borrower1.deleteBorrowedBook(book1);
        assertEquals((Boolean) true, borrower1.getRequestedBookList().isEmpty());

    }
    @Test
    public void SetRating() {
        Borrower borrower1 = new Borrower();
        borrower1.setBorrowerRating((float) 3.3);
        assertEquals((float) 3.3, borrower1.getBorrowerRating(), (float) 0.0001);
    }

    @Test
    public void getRating(){
       Borrower borrower1 = new Borrower((float)5.5);
        assertEquals( borrower1.getBorrowerRating(), (float)5.5, 0.0001);
    }


}