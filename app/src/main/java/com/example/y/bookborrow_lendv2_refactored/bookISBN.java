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

package com.example.y.bookborrow_lendv2_refactored;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

/**
 *  DO NOT CHANGE ANY INTERFACES FOR THE GETTING AND SETTING
 *  UNLESS YOU ARE GOING TO COMPLETE THE CHANGES IN OTHER ACTIVITY
 *
 *  This class is created for rating to the content of books marked with the same ISBN
 *
 */
public class bookISBN {
    private String ISBN;
    private Double totalRate = 0.00;
    private Integer borrowTime = 0;
    private ArrayList<RatingAndComment> commentList = new ArrayList<>();
    private FirebaseDatabase m;
    private DatabaseReference r;

    /**
     * This empty constructor will also be used
     * when trying to get a instance of the object from firebase
     */
    bookISBN(){}

    /**
     * This constructor is only use in EditBookDetail when init a new book
     * @see EditBookDetail
     * @param ISBN
     */
    bookISBN(String ISBN){
        this.ISBN = ISBN;
    }


    public void setBookRate(Double rating) {
        this.totalRate += rating;
        this.borrowTime += 1;
        m = FirebaseDatabase.getInstance();
        r = m.getReference("bookISBN/"+this.ISBN);
        r.child("totalRate").setValue(this.totalRate);
        r.child("borrowTime").setValue(this.borrowTime);

    }

    /**
     *  The getter is only used when try to get totalRate from firebase
     *  i.e. onDataChange method
     * @return
     */
    public Double getTotalRate(){return this.totalRate;}

    /**
     *  The getter is only used when try to get borrowTime from firebase
     *  i.e. onDataChange method
     * @return
     */
    public Integer getBorrowTime(){return borrowTime;}

    /**
     *  The getter is only used when try to get ISBN from firebase
     *  i.e. onDataChange method
     * @return
     */
    public String getISBN(){return ISBN;};

    /**
     *  return the book rate by calculate the avarage
     * @return
     */
    public String getBookRate() {
        if (this.totalRate == 0.00){
            return "No one rate it yet!";
        }
        return Double.toString(this.totalRate/this.borrowTime);
    }

    /**
     *  set all the class attribute to firebase
     */
    public void setToFirebase(){
        m = FirebaseDatabase.getInstance();
        r = m.getReference("bookISBN/"+this.ISBN);
        r.child("ISBN").setValue(this.ISBN);
        r.child("totalRate").setValue(this.totalRate);
        r.child("borrowTime").setValue(this.borrowTime);
    }

    /**
     *  get all a new RatingAndComment object
     *  set it to commentList or set it to firebase
     * @param c
     */
    public void addNewComment(RatingAndComment c){
        UUID commentID = UUID.randomUUID();
        commentList.add(c);
        m = FirebaseDatabase.getInstance();
        r = m.getReference("bookISBN/"+this.ISBN + "/RatingAndComment/"+commentID);
        r.child("ID").setValue(c.getID());
        r.child("rating").setValue(c.getRating());
        r.child("comment").setValue(c.getComment());
    }

}
