package com.example.y.bookborrow_lendv2;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RatingAndComment {
    private String ID;
    private Double rating;
    private String comment = null;

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
        this.rating = borrowerRating;
        return true;
    }

    public String getRating() {
        return Double.toString(rating);
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
}
