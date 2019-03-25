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
 * lender object class extends user class
 * contain two ArrayList borrowedBook and requestedList
 *
 *
 *  Created  on 2/15/19.
 *  @since 1.0
 * @see user
 */
public class lender extends user {
    private Double totalRate = 0.00000001;
    private Integer lendBookTime = 0;
    private ArrayList<RatingAndComment> commentList = new ArrayList<>();
    private FirebaseDatabase m;
    private DatabaseReference r;

    //private String uid;
    private ArrayList<String> lentBook = new ArrayList<>();
    private ArrayList<String> requestedBookList = new ArrayList<>();
    private ArrayList<String> MyBookList = new ArrayList<>();
    //private static lender instance;


    lender() {
    }

    public Double getTotalRate(){return this.totalRate;}

    public Integer getLendBookTime(){return lendBookTime;}

    public void setToFirebase(String uid,String email){
        FirebaseDatabase m = FirebaseDatabase.getInstance();
        DatabaseReference r = m.getReference("lenders/"+uid);
        r.child("email").setValue(email);
        r.child("uid").setValue(uid);
        r.child("totalRate").setValue(this.totalRate);
        Log.i("totalRate",Double.toString(this.totalRate));
        r.child("lendBookTime").setValue(this.lendBookTime);
        Log.i("totalRate",Double.toString(this.lendBookTime));
    }

    public void setNameToFireBase(String uid,String name){
        FirebaseDatabase m = FirebaseDatabase.getInstance();
        DatabaseReference r = m.getReference("lenders/"+uid);
        r.child("name").setValue(name);
    }


    /**
     * set the rating of the lender
     * @param rating
     */
    public void setLenderRating(Double rating) {
        this.totalRate += rating;
        this.lendBookTime += 1;
        m = FirebaseDatabase.getInstance();
        r = m.getReference("lenders/"+this.getUid());
        r.child("totalRate").setValue(this.totalRate);
        r.child("lendBookTime").setValue(this.lendBookTime);
    }

    public void addNewComment(RatingAndComment c){
        UUID commentID = UUID.randomUUID();
        commentList.add(c);
        m = FirebaseDatabase.getInstance();
        Log.i("testnn lender class",this.getUid());
        r = m.getReference("lenders/"+this.getUid() + "/RatingAndComment/"+commentID);
        r.child("ID").setValue(c.getID());
        r.child("rating").setValue(c.getRating());
        r.child("comment").setValue(c.getComment());
    }

    /**
     * return the rating of the lender
     * @return rating
     */
    public String getLenderRating() {
        if (this.totalRate == 0.00000001){
            return "No one lend his lending yet!";
        }
        return Double.toString(this.totalRate/lendBookTime);
    }


    /**
     * add the lender's own book to a list
     * @param s
     */
    public void addToMyBookList(String s){
        MyBookList.add(s);
    }

    /**
     * remove a book from the list of bokks the lender owns
     * @param s
     */
    public void removeFromMyBookList(String s){
        MyBookList.remove(s);
    }

    /**
     * set a list of books the lender onws and are requested by other users
     * @param list
     */

    public void setRequestedBookList(ArrayList<String> list) {
        requestedBookList = list;
    }

    /**
     * set a list of books the lender has lended
     * @param list
     */

    public void setLentBook(ArrayList<String> list) {
        lentBook = list;
    }

    /**
     * return a list of books the lender has lended
     * @return lentBook
     */

    public ArrayList<String> getLentBook() {
        return lentBook;
    }

    /**
     * return a list of books that are requested by other users
     * @return requestedBookList
     */
    public ArrayList<String> getRequestedBookList() {
        return requestedBookList;
    }


    /**
     * add a book to the list of books that are lended by the lender
     * @param book
     */
    public void addLentBook(String book) {
        lentBook.add(book);
    }

    /**
     * return a list of books the lender onws and are requested by other users
     * @return list
     */

    public void addRequestedBook(String book) {
        requestedBookList.add(book);
    }

    /**
     * remove the book from the list of books requested by other users
     * @param book
     */
    public void deleteRequestedBook(String book) {
        requestedBookList.remove(book);
    }


    /**
     * remove a book from the list of books that are lended by the lender
     * @param book
     */
    public void deleteLentBook(String book) {
        lentBook.remove(book);
    }

    /**
     * create a static instance of lender, singleton pattern has implemented
     * @return instance of lender
     */


    /*
    public static lender Instance()
    {
        //if no instance is initialized yet then create new instance
        //else return stored instance
        if (instance == null)
        {
            instance = new lender();
        }
        return instance;
    }*/


}

