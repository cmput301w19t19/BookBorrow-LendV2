package com.example.y.bookborrow_lendv2;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * The type Rating and comment.
 */
public class RatingAndComment {
    private String borrowerID;
    private Double borrowerRating;
    private String comment;
    //private FirebaseDatabase m = FirebaseDatabase.getInstance();

    /**
     * Instantiates a new Rating and comment.
     */
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

    /**
     * Sets borrower id.
     *
     * @param borrowerID the borrower id
     */
    public void setBorrowerID(String borrowerID) {
        this.borrowerID = borrowerID;
    }

    /**
     * Gets borrower id.
     *
     * @return the borrower id
     */
    public String getBorrowerID() {
        return borrowerID;
    }

    /**
     * Sets borrower rating.
     *
     * @param borrowerRating the borrower rating
     * @return the borrower rating
     */
    public boolean setBorrowerRating(Double borrowerRating) {
        if (borrowerRating < 0 || borrowerRating >10){
            return false;
        }
        this.borrowerRating = borrowerRating;
        return true;
    }

    /**
     * Gets borrower rating.
     *
     * @return the borrower rating
     */
    public String getBorrowerRating() {
        return Double.toString(borrowerRating);
    }

    /**
     * Sets comment.
     *
     * @param comment the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets comment.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }
}
