package com.example.y.bookborrow_lendv2;

import java.util.ArrayList;

public class lender extends user {
    private float lenderRating;
    private ArrayList<book> lendedBook;
    private ArrayList<book> requestedBookList;

    lender(){}
    public void setLenderRating(Float rating){
        this.lenderRating = rating;
    }

    public float getLenderRating(){return lenderRating;}


    public void addBorrowedBook(book book){
        lendedBook.add(book);
    }

    public void addRequestedBook(book book){
        requestedBookList.add(book);
    }

}

