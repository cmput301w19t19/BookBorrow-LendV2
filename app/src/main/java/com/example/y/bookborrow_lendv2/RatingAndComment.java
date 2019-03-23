package com.example.y.bookborrow_lendv2;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RatingAndComment {
    private String borrowerID;
    private Double borrowerRating;
    private String comment;
    //private FirebaseDatabase m = FirebaseDatabase.getInstance();

    RatingAndComment(){}

    /*
    RatingAndComment(String ID){
        this.setBorrowerID(ID);
    }

    public void setToFirebase(){
        DatabaseReference r1 = m.getReference("RatingAndComment/"+this.borrowerID);
        r1.child("borrowerRating").setValue(Double.toString(this.borrowerRating));
        r1.child("comment").setValue(this.comment);
    }

    public void deleteFromFirebase(){
        DatabaseReference r = m.getReference("RatingAndComment/").child(this.borrowerID);
        r.removeValue();
    }*/

    public void setBorrowerID(String borrowerID) {
        this.borrowerID = borrowerID;
    }

    public String getBorrowerID() {
        return borrowerID;
    }

    public boolean setBorrowerRating(Double borrowerRating) {
        if (borrowerRating < 0 || borrowerRating >10){
            return false;
        }
        this.borrowerRating = borrowerRating;
        return true;
    }

    public String getBorrowerRating() {
        return Double.toString(borrowerRating);
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
}
