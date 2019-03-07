package com.example.y.bookborrow_lendv2;


import android.media.Image;
import java.util.UUID;
import java.util.ArrayList;

public class book {
    private UUID ID;
    private String name;
    private Image photo;
    private String author;
    private String ISBN;
    private float longitude;
    private float latitude;
    private String description;
    private String title;
    private ArrayList<borrower> requestedList = new ArrayList<borrower>();
    private String borrowerName;
    private String ownerName;
    private String status;

    /**
     * A constructor with no parameters
     */
    book(){
        this.ID = UUID.randomUUID();
    };


    /**
     * This constructor is built for writing unit tests, we will use another constructor which doesn't have parameters
     * since there are too many parameters in this constructor
     */
    book(String name, String author, String ISBN, float longitude, float latitude, String description
    ,String title, float bookRating, String borrowerName, String ownerName, String status ,Image photo) {
        this.name = name;
        this.photo = photo;
        this.author = author;
        this.ISBN = ISBN;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.title = title;
        //this.bookRating = bookRating;
        this.borrowerName = borrowerName;
        this.ownerName = ownerName;
        this.status = status;
        this.photo = photo;
    }



    public void setRequestedList(ArrayList<borrower> list) {
        requestedList = list;
    }

    public void setBorrowerName(String name) {
        borrowerName = name;
    }

    public String getBorrowerName() {
        return borrowerName;
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

    public void setOwnerName(String name) {
        ownerName = name;
    }

    public String getOwnerName() {
        return ownerName;
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

