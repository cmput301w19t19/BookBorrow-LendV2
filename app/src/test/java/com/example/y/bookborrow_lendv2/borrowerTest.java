package com.example.y.bookborrow_lendv2;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;


import static org.junit.Assert.assertEquals;


import static org.junit.Assert.*;


public class borrowerTest {



   @Test
    public void set_getRequestedBookList() {
        borrower borrower1 = new borrower();
        ArrayList<book> books = new ArrayList<book>();
        borrower1.setRequestedBookList(books);
        assertEquals(books,borrower1.getRequestedBookList());
    }

    @Test
    public void set_getBorrowedBook() {
        borrower borrower1 = new borrower();
        ArrayList<book> books = new ArrayList<book>();
        borrower1.setBorrowedBook(books);
        assertEquals(books,borrower1.getBorrowedBook());
    }


    @Test
    public void addBorrowedBook() {
        book book1 = new book();
        borrower borrower1 = new borrower();

        borrower1.addBorrowedBook(book1);
        assertTrue(borrower1.getBorrowedBook().contains(book1));
    }

    @Test
    public void addRequestedBook() {
        book book1 = new book();
        borrower borrower1 = new borrower();

        borrower1.addRequestedBook(book1);

        assertTrue(borrower1.getRequestedBookList().contains(book1));
    }

    @Test
    public void deleteRequestedBook() {
        book book1 = new book();
        borrower borrower1 = new borrower();
        borrower1.addRequestedBook(book1);
        borrower1.deleteRequestedBook(book1);
        assertTrue(borrower1.getRequestedBookList().isEmpty());
    }

    @Test
    public void deleteBorrowedBook() {

        borrower borrower1 = new borrower();
        book book1 = new book();

        borrower1.deleteBorrowedBook(book1);
        assertEquals((Boolean) true, borrower1.getRequestedBookList().isEmpty());

    }


    @Test
    public void set_getAcceptedList_AddAcceptedBook(){
        borrower borrower1 = new borrower();
        book book1 = new book();
        ArrayList<book> list = new ArrayList<book>();
        borrower1.setAcceptedBookList(list);
        borrower1.addAcceptedBook(book1);
        ArrayList<book> list2 = new ArrayList<book>();
        list2 = borrower1.getAcceptedBookList();
        assertEquals(list, list2);

    }


}