package com.example.y.bookborrow_lendv2;

import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.*;

public class lenderTest {

    @Test
    public void get_setRequestedBookList() {
        lender lender1 = new lender();
        ArrayList<String> books = new ArrayList<String>();
        lender1.setRequestedBookList(books);
        assertEquals(books,lender1.getRequestedBookList());
    }

    @Test
    public void get_setLentBook() {
        lender lender1 = new lender();
        ArrayList<String> books = new ArrayList<String>();
        lender1.setLentBook(books);
        assertEquals(books,lender1.getLentBook());
    }


    @Test
    public void addAndDeleteLentBook() {
        String bookName = "test";
        lender lender1 = new lender();

        lender1.addLentBook(bookName);
        assertTrue(lender1.getLentBook().contains(bookName));
        lender1.deleteLentBook(bookName);
        assertTrue(lender1.getLentBook().isEmpty());
    }

    @Test
    public void addAndDeleteRequestedBook() {
        String bookName = "test";
        lender lender1 = new lender();

        lender1.addRequestedBook(bookName);
        assertTrue(lender1.getRequestedBookList().contains(bookName));
        lender1.deleteRequestedBook(bookName);
        assertTrue(lender1.getRequestedBookList().isEmpty());
    }

}

