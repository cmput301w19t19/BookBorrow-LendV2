package com.example.y.bookborrow_lendv2;

import java.util.ArrayList;

public class Book {

    private ArrayList<String> requestList = new ArrayList<String>();

    private String owner;
    private String borrower;
    private double rating;

    //
    private String status;

    private String location;

    private String description;

    private String ISBN;

    private String author;

    private String title;

    public void setOwner(String owner){

        this.owner = owner;
    }

    

}
