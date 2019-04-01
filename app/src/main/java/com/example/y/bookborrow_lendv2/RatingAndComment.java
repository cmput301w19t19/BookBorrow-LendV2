/*
 * Class RatingAndComment.java
 *
 * Version 2.0
 *
 * Date 2019.4.1
 *
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 *  DO NOT CHANGE ANY INTERFACES FOR THE GETTING AND SETTING
 *  UNLESS YOU ARE GOING TO COMPLETE THE CHANGES IN OTHER ACTIVITY
 *
 *  This class is a container.
 *  A RatingAndComment can be made to a book, a lender or a borrower:
 *  A RatingAndComment to a book is for the content:
 *  Is the book intersting? Is the book attractive?
 *
 *  A RatingAndComment to a lender is
 *  for the copy of the book that the lender rent to others,
 *  for the communication experience when borroring a lender's book
 *  and for the lender's attitude toward sharing his or hers book
 *
 *  A RatingAndComment to a borrower is for
 *  the borrower's attitude towards using other's book
 *  and the communication experience when lending this borrower books
 *
 *  ID is the unique ID of any user who tend to make the rate and comment
 *  A rating is double object BUT THE GETTER FOR RATING RETURNS A STRING FOR SHOING IT IN TEXTVIEW
 *  A comment is a string with any numbers of words inside
 *
 */
public class RatingAndComment {
    private String ID;
    private String rating;
    private String comment = null;

    /**
     * This empty constructor is used in RateToOwner and RateToBorrower
     * This empty constructor will also be used
     * when trying to get a instance of the object from firebase
     * @see RateToBorrower
     * @see RateToOwner
     */
    RatingAndComment(){}

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return this.ID;
    }

    public boolean setRating(Double borrowerRating) {
        if (borrowerRating < 0 || borrowerRating >10){
            return false;
        }
        this.rating = Double.toString(borrowerRating);
        return true;
    }

    public String getRating() {
        return rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
}
