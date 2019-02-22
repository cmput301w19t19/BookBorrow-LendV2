package com.example.y.bookborrow_lendv2;


import android.media.Image;

import java.util.ArrayList;

public class book {
    private String name;
    private Image photo;
    private String author;
    private String ISBN;
    private float longitude;
    private float latitude;
    private String description;
    private String title;
    private float bookRating;
    private ArrayList<borrower> requestedList = new ArrayList<borrower>();
    private String borrower;
    private String owner;
    private String status;


    book() {
    }

    public void setBookRating(Float rating) {
        this.bookRating = rating;
    }

    public void setRequestedList(ArrayList<borrower> list) {
        requestedList = list;
    }

    public void setBorrower(String name) {
        borrower = name;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setISBN(String ISBN_code) {
        ISBN = ISBN_code;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setDescription(String desc) {
        description = desc;
    }

    public String getDescription() {
        return description;
    }

    public void setLongitude(float longi) {
        longitude = longi;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLatitude(float lati) {
        latitude = lati;
    }

    public float getLatitude() {
        return latitude;
    }

    public ArrayList<borrower> getRequestedList() {
        return requestedList;
    }

    public float getBookRating() {
        return bookRating;
    }

    public void setOwner(String name) {
        owner = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setTitle(String string) {
        title = string;
    }

    public String getTitle() {
        return title;
    }

    public void setStatus(String t_status) {
        status = t_status;
    }

    public String getStatus() {
        return status;
    }

    public void addRequested(borrower name) {
        requestedList.add(name);
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAuthor(String name) {
        this.author = name;
    }

    public String getAuthor() {
        return author;
    }

    public Image getPhoto() {
        return photo;
    }
}

