package com.example.y.BookEX;

/**
 *  DO NOT CHANGE ANY INTERFACES FOR THE GETTING AND SETTING
 *  UNLESS YOU ARE GOING TO COMPLETE THE CHANGES IN OTHER ACTIVITY
 *
 *  This class is a container.
 *  A RatingAndComment can be made to a Book, a Lender or a Borrower:
 *  A RatingAndComment to a Book is for the content:
 *  Is the Book intersting? Is the Book attractive?
 *
 *  A RatingAndComment to a Lender is
 *  for the copy of the Book that the Lender rent to others,
 *  for the communication experience when borroring a Lender's Book
 *  and for the Lender's attitude toward sharing his or hers Book
 *
 *  A RatingAndComment to a Borrower is for
 *  the Borrower's attitude towards using other's Book
 *  and the communication experience when lending this Borrower books
 *
 *  ID is the unique ID of any User who tend to make the rate and comment
 *  A rating is double object BUT THE GETTER FOR RATING RETURNS A STRING FOR SHOING IT IN TEXTVIEW
 *  A comment is a string with any numbers of words inside
 *
 */
public class RatingAndComment {
    private String ID;
    private Double rating;
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
