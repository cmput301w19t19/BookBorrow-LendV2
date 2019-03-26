package com.example.y.BookEX;

import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.*;

public class lenderTest {

    @Test
    public void get_setLenderRating() {
        Lender lender1 = new Lender();
        lender1.setLenderRating((float) 3.3);
        assertEquals((float) 3.3, lender1.getLenderRating(), (float) 0.0001);
    }

    @Test
    public void get_setRequestedBookList() {
        Lender lender1 = new Lender();
        ArrayList<String> books = new ArrayList<String>();
        lender1.setRequestedBookList(books);
        assertEquals(books,lender1.getRequestedBookList());
    }

    @Test
    public void get_setLentBook() {
        Lender lender1 = new Lender();
        ArrayList<String> books = new ArrayList<String>();
        lender1.setLentBook(books);
        assertEquals(books,lender1.getLentBook());
    }


    @Test
    public void addLentBook() {
        Book book1 = new Book();
        Lender lender1 = new Lender();

        lender1.addLentBook(book1.getName());
        assertTrue(lender1.getLentBook().contains(book1));
    }

    @Test
    public void addRequestedBook() {
        Book book1 = new Book();
        Lender lender1 = new Lender();

        lender1.addRequestedBook(book1.getName());
        assertTrue(lender1.getRequestedBookList().contains(book1));
    }

    @Test
    public void deleteRequestedBook() {
        Book book1 = new Book();
        Lender lender1 = new Lender();

        lender1.addRequestedBook(book1.getName());
        lender1.deleteRequestedBook(book1.getName());
        assertTrue(lender1.getRequestedBookList().isEmpty());
    }

    @Test
    public void deleteLentBook() {
        Book book1 = new Book();
        Lender lender1 = new Lender();
        lender1.addLentBook(book1.getName());
        lender1.deleteLentBook(book1.getName());
        assertTrue(lender1.getLentBook().isEmpty());
    }

}

