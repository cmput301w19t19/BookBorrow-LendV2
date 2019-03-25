/*
 * Copyright 2019 TEAM19
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.example.y.bookborrow_lendv2;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;


/**
 * book object class
 * contain all book related attributes
 * setter and getter for the book info.
 * And firebase related methods include:
 * setToFirebase , deleteFromFirebase
 * <p>
 * * Created  on 2/15/19.
 * * @since 1.0
 */
public class book {
    private UUID ID;
    private String name = null;
    private Bitmap photo;
    private String author = null;
    private String ISBN = null;
    private Double longitude = 0.0;
    private Double latitude = 0.0;
    private String description = null;
    private String title = null;
    private ArrayList<String> requestedList = new ArrayList<String>();
    private String borrowerID= null;
    private String ownerID = null;
    private String status = "available";
    private Double rating = -1.0;
    private FirebaseDatabase m;
    private DatabaseReference r;
    private String firstScanned = "false";

    private Map<String, Boolean> requestList;


    /**
     * A constructor with no parameters
     */
    book(){
        this.ID = UUID.randomUUID();
    }

    /**
     * Instantiates a new Book.
     *
     * @param id the id
     */
    book(String id){
        this.ID = UUID.fromString(id);
    };


    /**
     * This constructor is built for writing unit tests, we will use another constructor which doesn't have parameters
     * since there are too many parameters in this constructor
     *
     * @param name         the name
     * @param author       the author
     * @param ISBN         the isbn
     * @param longitude    the longitude
     * @param latitude     the latitude
     * @param description  the description
     * @param title        the title
     * @param bookRating   the book rating
     * @param borrowerName the borrower name
     * @param ownerName    the owner name
     * @param status       the status
     * @param firstScanned the first scanned
     */
    book(String name, String author, String ISBN, Double longitude, Double latitude, String description
            , String title, Double bookRating, String borrowerName, String ownerName, String status, String firstScanned) {
        this.name = name;
        //this.photo = photo;
        this.author = author;
        this.ISBN = ISBN;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.title = title;
        //this.bookRating = bookRating;
        this.borrowerID = borrowerName;
        this.ownerID = ownerName;
        this.status = status;
        this.firstScanned = firstScanned;
    }

    /**
     * Set image.
     *
     * @param bitmap the bitmap
     */
    public void setImage(Bitmap bitmap){
        this.photo = bitmap;
    }

    /**
     * Get image bitmap.
     *
     * @return the bitmap
     */
    public Bitmap getImage(){
        return this.photo;
    }

    /**
     * this method set the values that fot from firebase to object
     */

    public void setToFirebase(){
        m = FirebaseDatabase.getInstance();
        r = m.getReference("book/"+this.getID());
        r.child("id").setValue(this.getID());
        r.child("bookRating").setValue(Double.toString(this.rating));
        r.child("name").setValue(this.name);
        r.child("author").setValue(this.author);
        r.child("ISBN").setValue(this.ISBN);
        r.child("longitude").setValue(this.longitude);
        r.child("latitude").setValue(this.latitude);
        r.child("description").setValue(this.description);
        r.child("title").setValue(this.title);
        r.child("borrowerID").setValue(this.borrowerID);
        r.child("ownerID").setValue(this.ownerID);
        r.child("status").setValue(this.status);
        r.child("firstScanned").setValue(this.firstScanned);
    }

    /**
     * Sets id.
     *
     * @param s this method set a unique id to book
     */

    public void setID(String s) {
        this.ID = UUID.fromString(s);
    }

    /**
     * this method return book ID
     *
     * @return book 's ID
     */
    public String getID(){return this.ID.toString();}


    /**
     * this method set a array of books that are requested by users except owner
     *
     * @param list the list
     */
    public void setRequestedList(ArrayList<String> list) {
        requestedList = list;
    }

    /**
     * this method set the name of user who has borrowed this book
     *
     * @param name the name
     */
    public void setBorrowerID(String name) {
        borrowerID = name;
    }

    /**
     * this method return the name of user who has borrowed this book
     *
     * @return borrower id
     */
    public String getBorrowerID() {
        return borrowerID;
    }

    /**
     * this method set the ISBN code of the book
     *
     * @param ISBN_code the isbn code
     */
    public void setISBN(String ISBN_code) {
        ISBN = ISBN_code;
    }


    /**
     * this method return the ISBN code of the book
     *
     * @return ISBN_code isbn
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * this method set the description code of the book
     *
     * @param desc the desc
     */
    public void setDescription(String desc) {
        description = desc;
    }


    /**
     * this method return the description of the book
     *
     * @return description description
     */
    public String getDescription() {
        return description;
    }


    /**
     * this method set the longitude( one elemente of the coordinate which shows the location) of the book
     *
     * @param longi the longi
     */
    public void setLongitude(Double longi) {
        longitude = longi;
    }

    /**
     * this method return the longitude( one elemente of the coordinate shows the location) of the book
     *
     * @return longi longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * this method set the latitude( one elemente of the coordinate  shows the location) of the book
     *
     * @param lati the lati
     */
    public void setLatitude(Double lati) {
        latitude = lati;
    }

    /**
     * this method return the latitude( one elemente of the coordinate  shows the location) of the book
     *
     * @return latitude latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * this method return the array list of books
     *
     * @return a list of books that are requested by users except owner
     */
    public ArrayList<String> getRequestedList() {
        requestedList = new ArrayList<String>(requestList.keySet());
        return requestedList;
    }

    /**
     * set the owner's name of the book
     *
     * @param name the name
     */
    public void setOwnerID(String name) {
        ownerID = name;
    }

    /**
     * get the owner's name of the book
     *
     * @return owner id
     */


    public String getOwnerID() {
        return ownerID;
    }

    /**
     * set the status of the book to "requested"
     */
    public void setStatusToRequested(){
        this.status = "requested";
        this.setToFirebase();
    }

    /**
     * set the status of the book to "available"
     */


    public void setStatusToAvailable(){
        this.status = "available";
        this.setToFirebase();
    }

    /**
     * set the status of the book to "accepted"
     */
    public void setStatusToAccepted(){
        this.status = "accepted";
        this.setToFirebase();
    }

    /**
     * set the status of the book to "borrowed"
     */

    public void setStatusToborrowed(){
        this.status = "borrowed";
        this.setToFirebase();
    }

    /**
     * return the status of the book
     *
     * @return status status
     */
    public String getStatus() {
        return status;
    }

    /**
     * add user's name who has made a request to borrow this book to a list
     *
     * @param name the name
     */
    public void addRequested(String name) {
        requestedList.add(name);
    }


    /**
     * set title(name) of this book
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * return the name of the book
     *
     * @return name name
     */
    public String getName() {
        return name;
    }

    /**
     * set author's name of this book
     *
     * @param name the name
     */
    public void setAuthor(String name) {
        this.author = name;
    }

    /**
     * return the author's name of this book
     *
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets first scanned.
     *
     * @param s this method set a checkmate of borrowing or returning
     */
    public void setFirstScanned(String s) {
        this.firstScanned = s;
    }

    /**
     * this method return checkmate of borrowing or returning
     *
     * @return firstScanned string
     */
    public String getFirstScanned(){return this.firstScanned;}

    /**
     * return the book's rating
     *
     * @return rating string
     */
    public String getBookRating(){
        bookISBN ISBN = new bookISBN(this.getISBN());
        return ISBN.getBookRate();
    }

    /**
     * return the description which contains the book's author, title
     *
     * @return a author name concatenated with book's title of type string
     */
    public String getDescriptionBundle(){
        return this.getAuthor()+"\n"+this.getName()+"\n";//+this.getISBN();
    }

    /**
     * delete the book from firebase realtime database
     *
     * @return true boolean
     */
    public boolean deleteFromFirebase(){
        m = FirebaseDatabase.getInstance();
        r = m.getReference("book").child(this.getID());
        r.removeValue();

        return true;
    }
}
