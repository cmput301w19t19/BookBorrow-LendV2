package com.example.y.bookborrow_lendv2;

import java.util.ArrayList;

public class borrower extends user {
    private float borrowerRating;
    private ArrayList<book> borrowedBook;
    private ArrayList<book> requestedBookList;

    borrower(){}
    public void setBorrowerRating(Float rating){
        this.borrowerRating = rating;
    }


    public void addBorrowedBook(book book){
        borrowedBook.add(book);
    }

    public void addRequestedBook(book book){
        requestedBookList.add(book);
    }

    public float getBorrowerRating() {
        return borrowerRating;
    }
}
