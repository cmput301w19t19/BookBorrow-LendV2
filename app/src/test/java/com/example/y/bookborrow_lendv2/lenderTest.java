package com.example.y.bookborrow_lendv2;

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
        ArrayList<Book> Books = new ArrayList<Book>();
        lender1.setRequestedBookList(Books);
        assertEquals(Books,lender1.getRequestedBookList());
    }

    @Test
    public void get_setLentBook() {
        Lender lender1 = new Lender();
        ArrayList<Book> Books = new ArrayList<Book>();
        lender1.setLentBook(Books);
        assertEquals(Books,lender1.getLentBook());
    }


    @Test
    public void addLentBook() {
        Book book1 = new Book();
        Lender lender1 = new Lender();

        lender1.addLentBook(book1);
        assertTrue(lender1.getLentBook().contains(book1));
    }

    @Test
    public void addRequestedBook() {
        Book book1 = new Book();
        Lender lender1 = new Lender();

        lender1.addRequestedBook(book1);
        assertTrue(lender1.getRequestedBookList().contains(book1));
    }

    @Test
    public void deleteRequestedBook() {
        Book book1 = new Book();
        Lender lender1 = new Lender();

        lender1.addRequestedBook(book1);
        lender1.deleteRequestedBook(book1);
        assertTrue(lender1.getRequestedBookList().isEmpty());
    }

    @Test
    public void deleteLentBook() {
        Book book1 = new Book();
        Lender lender1 = new Lender();
        lender1.addLentBook(book1);
        lender1.deleteLentBook(book1);
        assertTrue(lender1.getLentBook().isEmpty());
    }

}

