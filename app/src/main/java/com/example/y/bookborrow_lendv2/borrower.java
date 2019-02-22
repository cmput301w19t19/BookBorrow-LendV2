package com.example.y.bookborrow_lendv2;


import java.util.ArrayList;

public class borrower extends user {
    private float borrowerRating;

    private ArrayList<book> borrowedBook = new ArrayList<book>();
    private ArrayList<book> requestedBookList = new ArrayList<book>();

    borrower() {
    }

    public void setBorrowerRating(Float rating) {
        this.borrowerRating = rating;
    }

    public void setRequestedBookList(ArrayList<book> list) {
        requestedBookList = list;
    }

    public void setBorrowedBook(ArrayList<book> list) {
        borrowedBook = list;
    }

    public ArrayList<book> getBorrowedBook() {
        return borrowedBook;
    }


    public ArrayList<book> getRequestedBookList() {
        return requestedBookList;
    }

    public void addBorrowedBook(book book) {
        borrowedBook.add(book);
    }

    public void addRequestedBook(book book) {
        requestedBookList.add(book);
    }

    public void deleteRequestedBook(book book) {
        requestedBookList.remove(book);
    }

    public void deleteBorrowedBook(book book) {
        borrowedBook.remove(book);
    }

    public float getBorrowerRating() {
        return borrowerRating;
    }
}
