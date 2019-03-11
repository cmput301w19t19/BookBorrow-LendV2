package com.example.y.bookborrow_lendv2;


import java.util.ArrayList;

public class Borrower extends User {
    private float borrowerRating;

    private ArrayList<Book> borrowedBook = new ArrayList<Book>();
    private ArrayList<Book> requestedBookList = new ArrayList<Book>();
    private String uid;
    private static Borrower instance;



    /** A constructor with no parameters*/
    Borrower() {
    }

    /**
     * another constructor with parameter "rating"
     * @param rating
     */
    Borrower(float rating){
        this.borrowerRating = rating;
    }



    public void setBorrowerRating(Float rating) {
        this.borrowerRating = rating;
    }

    public void setRequestedBookList(ArrayList<Book> list) {
        requestedBookList = list;
    }

    public void setBorrowedBook(ArrayList<Book> list) {
        borrowedBook = list;
    }

    public ArrayList<Book> getBorrowedBook() {
        return borrowedBook;
    }


    public ArrayList<Book> getRequestedBookList() {
        return requestedBookList;
    }

    public void addBorrowedBook(Book book) {
        borrowedBook.add(book);
    }

    public void addRequestedBook(Book book) {
        requestedBookList.add(book);
    }

    public void deleteRequestedBook(Book book) {
        requestedBookList.remove(book);
    }

    public void deleteBorrowedBook(Book book) {
        borrowedBook.remove(book);
    }

    public float getBorrowerRating() {
        return borrowerRating;
    }


    public static Borrower Instance()
    {
        //if no instance is initialized yet then create new instance
        //else return stored instance
        if (instance == null)
        {
            instance = new Borrower();
        }
        return instance;
    }
}
