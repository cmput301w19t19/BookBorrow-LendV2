package com.example.y.bookborrow_lendv2;

import java.util.ArrayList;

public class Lender extends User {
    private float lenderRating;
    private ArrayList<String> lentBook = new ArrayList<>();
    private ArrayList<String> requestedBookList = new ArrayList<>();
    private ArrayList<String> MyBookList = new ArrayList<>();
    private static Lender instance;


    Lender() {
    }

    public void setLenderRating(Float rating) {
        this.lenderRating = rating;
    }

    public float getLenderRating() {
        return lenderRating;
    }

    //public void getMyBookList

    public void addToMyBookList(String s){
        MyBookList.add(s);
    }

    public void removeFromMyBookList(String s){
        MyBookList.remove(s);
    }

    public void setRequestedBookList(ArrayList<String> list) {
        requestedBookList = list;
    }

    public void setLentBook(ArrayList<String> list) {
        lentBook = list;
    }

    public ArrayList<String> getLentBook() {
        return lentBook;
    }

    public ArrayList<String> getRequestedBookList() {
        return requestedBookList;
    }

    public void addLentBook(String book) {
        lentBook.add(book);
    }

    public void addRequestedBook(String book) {
        requestedBookList.add(book);
    }

    public void deleteRequestedBook(String book) {
        requestedBookList.remove(book);
    }

    public void deleteLentBook(String book) {
        lentBook.remove(book);
    }

    public static Lender Instance()
    {
        //if no instance is initialized yet then create new instance
        //else return stored instance
        if (instance == null)
        {
            instance = new Lender();
        }
        return instance;
    }


}

