package com.example.y.bookborrow_lendv2;

public class B_request {
    private String userName;
    private double rating;
    boolean selected = false;
    private String userID;

    public B_request(String userName, double rating, String userID) {
        this.userName = userName;
        this.rating = rating;
        this.userID = userID;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public double getRating(){
        return rating;
    }

    public void setRating(double rating){
        this.rating = rating;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getUserID(){
        return this.userID;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }
}
