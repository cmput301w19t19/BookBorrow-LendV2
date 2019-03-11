package com.example.y.bookborrow_lendv2;

import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.*;

public class lenderTest {

    @Test
    public void get_setLenderRating() {
        lender lender1 = new lender();
        lender1.setLenderRating((float) 3.3);
        assertEquals((float) 3.3, lender1.getLenderRating(), (float) 0.0001);
    }

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
    public void addLentBook() {
        book book1 = new book();
        lender lender1 = new lender();

        lender1.addLentBook(book1.getName());
        assertTrue(lender1.getLentBook().contains(book1));
    }

    @Test
    public void addRequestedBook() {
        book book1 = new book();
        lender lender1 = new lender();

        lender1.addRequestedBook(book1.getName());
        assertTrue(lender1.getRequestedBookList().contains(book1));
    }

    @Test
    public void deleteRequestedBook() {
        book book1 = new book();
        lender lender1 = new lender();

        lender1.addRequestedBook(book1.getName());
        lender1.deleteRequestedBook(book1.getName());
        assertTrue(lender1.getRequestedBookList().isEmpty());
    }

    @Test
    public void deleteLentBook() {
        book book1 = new book();
        lender lender1 = new lender();
        lender1.addLentBook(book1.getName());
        lender1.deleteLentBook(book1.getName());
        assertTrue(lender1.getLentBook().isEmpty());
    }

}

