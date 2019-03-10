package com.example.y.bookborrow_lendv2;


import android.media.Image;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;

public class book {
    private UUID ID;
    private String name = null;
    //private Image photo = null;
    private String author = null;
    private String ISBN = null;
    private Double longitude = 0.0;
    private Double latitude = 0.0;
    private String description = null;
    private String title = null;
    private ArrayList<String> requestedList = new ArrayList<String>();
    private String borrowerName = null;
    private String ownerName = null;
    private String status = "available";
    private Double rating = -1.0;
    private FirebaseDatabase m;
    private DatabaseReference r;
    private Map<String, Boolean> requestList;

    /**
     * A constructor with no parameters
     */
    book(){
        this.ID = UUID.randomUUID();
    }

    book(String id){

        this.ID = UUID.fromString(id);
    };


    /**
     * This constructor is built for writing unit tests, we will use another constructor which doesn't have parameters
     * since there are too many parameters in this constructor
     */
    book(String name, String author, String ISBN, Double longitude, Double latitude, String description
    ,String title, Double bookRating, String borrowerName, String ownerName, String status ) {
        this.name = name;
        //this.photo = photo;
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
    }

    public void setToFirebase(){
        m = FirebaseDatabase.getInstance();
        r = m.getReference("book");
        r.child(this.getID()).setValue(this);

    }

    public void setID(String s) {
        this.ID = UUID.fromString(s);
    }

    public String getID(){return this.ID.toString();}

    public void setRequestedList(ArrayList<String> list) {
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

    public void setLongitude(Double longi) {
        longitude = longi;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLatitude(Double lati) {
        latitude = lati;
    }

    public Double getLatitude() {
        return latitude;
    }

    public ArrayList<String> getRequestedList() {
        requestedList = new ArrayList<String>(requestList.keySet());
        return requestedList;
    }


    public void setOwnerName(String name) {
        ownerName = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setStatusToRequested(){
        this.status = "Requested";
        this.setToFirebase();
    }

    public void setStatusToAvailable(){
        this.status = "Available";
        this.setToFirebase();
    }

    public void setStatusToAccepted(){
        this.status = "Accepted";
        this.setToFirebase();
    }

    public void setStatusToborrowed(){
        this.status = "Borrowed";
        this.setToFirebase();
    }

    public String getStatus() {
        return status;
    }

    public void addRequested(String name) {
        requestedList.add(name);
    }

    /*public void setPhoto(Image photo) {
        this.photo = photo;
    } */

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

   /* public Image getPhoto() {
        return photo;
    } */

    public Double getBookRating(){return rating; }

    public String getDescriptionBundle(){
        return this.getAuthor()+"\n"+this.getName()+"\n";//+this.getISBN();
    }

    public boolean deleteFromFirebase(){
        m = FirebaseDatabase.getInstance();
        r = m.getReference("book").child(this.getID());
        r.removeValue();

        return true;
    }
}

