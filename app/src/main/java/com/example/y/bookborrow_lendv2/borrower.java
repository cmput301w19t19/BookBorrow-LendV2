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


import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

/**
 * borrower object class extends user class
 * contain two ArrayList borrowedBook and requestedList
 *
 * @param
 * @return none
 * @see user
 */
public class borrower extends user {
    private Double totalRate = 0.00000001;
    private Integer borrowBookTime = 0;
    private ArrayList<RatingAndComment> commentList = new ArrayList<RatingAndComment>();

    private ArrayList<book> borrowedBook = new ArrayList<book>();
    private ArrayList<book> requestedBookList = new ArrayList<book>();
    private ArrayList<book> acceptedBookList = new ArrayList<book>();
    private static borrower instance;
    private FirebaseDatabase m;
    private DatabaseReference r;


    /**
     * A constructor with no parameters
     */
    borrower() {
    }

    public Double getTotalRate(){return this.totalRate;}

    public Integer getborrowBookTime(){return borrowBookTime;}


    public void setToFirebase(String uid,String email){
        m = FirebaseDatabase.getInstance();
        r = m.getReference("borrowers/"+uid);
        r.child("email").setValue(email);
        r.child("uid").setValue(uid);
        r.child("totalRate").setValue(this.totalRate);
        r.child("borrowBookTime").setValue(this.borrowBookTime);
    }

    public void setNameToFireBase(String uid,String name){
        FirebaseDatabase m = FirebaseDatabase.getInstance();
        DatabaseReference r = m.getReference("borrowers/"+uid);
        r.child("name").setValue(name);
    }

    public void setBorrowerRating(Double rating) {
        this.totalRate += rating;
        this.borrowBookTime += 1;
        m = FirebaseDatabase.getInstance();
        r = m.getReference("borrowers/"+this.getUid());
        r.child("totalRate").setValue(this.totalRate);
        r.child("borrowBookTime").setValue(this.borrowBookTime);
    }

    public void addNewComment(RatingAndComment c){
        UUID commentID = UUID.randomUUID();
        commentList.add(c);
        m = FirebaseDatabase.getInstance();
        r = m.getReference("borrowers/"+this.getUid() + "/RatingAndComment/"+commentID);
        r.child("ID").setValue(c.getID());
        r.child("rating").setValue(c.getRating());
        r.child("comment").setValue(c.getComment());
    }


    /**
     * set the list of array list of books the borrower has requested
     *
     * @param list the list
     */
    public void setRequestedBookList(ArrayList<book> list) {
        requestedBookList = list;
    }

    /**
     * set the list of the books the borrower has borrowed
     *
     * @param list the list
     */
    public void setAcceptedBookList(ArrayList<book> list) {
        acceptedBookList = list;
    }

    /**
     * set the list of the books the borrower has borrowed
     *
     * @param list the list
     */
    public void setBorrowedBook(ArrayList<book> list) {
        borrowedBook = list;
    }

    /**
     * return the list of books the borrower has borrowed so far
     *
     * @return list borrowed book
     */
    public ArrayList<book> getBorrowedBook() {
        return borrowedBook;
    }

    /**
     * return the list of books the borrower has requested
     *
     * @return accepted book list
     */
    public ArrayList<book> getAcceptedBookList() {
        return acceptedBookList;
    }

    /**
     * return the list of books the borrower has requested
     *
     * @return requested book list
     */
    public ArrayList<book> getRequestedBookList() {
        return requestedBookList;
    }

    /**
     * add the book the borrower has borrowed to the list
     *
     * @param book the book
     */
    public void addBorrowedBook(book book) {
        borrowedBook.add(book);
    }

    /**
     * add the book the borrower has requested to the list
     *
     * @param book the book
     */

    public void addRequestedBook(book book) {
        requestedBookList.add(book);
    }

    /**
     * add the book the borrower has requested to the list
     *
     * @param book the book
     */
    public void addAcceptedBook(book book) {
        acceptedBookList.add(book);
    }

    /**
     * remove the book the borrower has requested from the list
     *
     * @param book the book
     */
    public void deleteRequestedBook(book book) {
        requestedBookList.remove(book);
    }

    /**
     * remove the book the borrower has borrowed from the list
     *
     * @param book the book
     */
    public void deleteBorrowedBook(book book) {
        borrowedBook.remove(book);
    }

    /**
     * return the rating of the borrower
     *
     * @return borrower rating
     */

    public String getBorrowerRating() {
        if (this.totalRate == 0.00000001){
            return "No one rate borrowring yet!";
        }
        String s = String.format("%.2f",this.totalRate/this.borrowBookTime);
        return s;
    }

    /**
     * create a static instance of borrower, singleton pattern has implemented
     *
     * @return instance of borrower
     */
    public static borrower Instance()
    {
        //if no instance is initialized yet then create new instance
        //else return stored instance
        if (instance == null)
        {
            instance = new borrower();
        }
        return instance;
    }
}
