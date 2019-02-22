package com.example.y.bookborrow_lendv2;

import java.util.ArrayList;

public class lender extends user {
    private float lenderRating;
    private ArrayList<book> lentBook = new ArrayList<>();
    private ArrayList<book> requestedBookList = new ArrayList<>();

    lender() {
    }

    public void setLenderRating(Float rating) {
        this.lenderRating = rating;
    }

    public float getLenderRating() {
        return lenderRating;
    }


    public void setRequestedBookList(ArrayList<book> list) {
        requestedBookList = list;
    }

    public void setLentBook(ArrayList<book> list) {
        lentBook = list;
    }

    public ArrayList<book> getLentBook() {
        return lentBook;
    }

    public ArrayList<book> getRequestedBookList() {
        return requestedBookList;
    }

    public void addLentBook(book book) {
        lentBook.add(book);
    }

    public void addRequestedBook(book book) {
        requestedBookList.add(book);
    }

    public void deleteRequestedBook(book book) {
        requestedBookList.remove(book);
    }

    public void deleteLentBook(book book) {
        lentBook.remove(book);
    }


}

