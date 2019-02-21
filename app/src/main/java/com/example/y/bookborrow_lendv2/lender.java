package com.example.y.bookborrow_lendv2;

import java.util.ArrayList;

public class lender extends user {
    private float lenderRating;
    private ArrayList<book> lendedBook;
    private ArrayList<book> requestedBookList;

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

    public void setLendedBook(ArrayList<book> list) {
        lendedBook = list;
    }

    public ArrayList<book> getLendededBook() {
        return lendedBook;
    }

    public ArrayList<book> getRequestedBookList() {
        return requestedBookList;
    }

    public void addlendedBook(book book) {
        lendedBook.add(book);
    }

    public void addRequestedBook(book book) {
        requestedBookList.add(book);
    }

    public void deleteRequestedBook(book book) {
        requestedBookList.remove(book);
    }

    public void deleteBorrowedBook(book book) {
        lendedBook.remove(book);
    }

    public float getBorrowerRating() {
        return lenderRating;

    }
}
