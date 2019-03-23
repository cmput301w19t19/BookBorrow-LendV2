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


import java.util.ArrayList;

/**
 * borrower object class extends user class
 * contain two ArrayList borrowedBook and requestedList
 * @param
 * @return none
 * @see user
 */

public class borrower extends user {
    private float borrowerRating;

    private ArrayList<book> borrowedBook = new ArrayList<book>();
    private ArrayList<book> requestedBookList = new ArrayList<book>();
    private ArrayList<book> acceptedBookList = new ArrayList<book>();
    private static borrower instance;



    /** A constructor with no parameters*/
    borrower() {
    }

    /**
     * another constructor with parameter "rating"
     * @param rating
     */
    borrower(float rating){
        this.borrowerRating = rating;
    }


    /**
     * set the rating of the bowrrower
     * @param rating
     */
    public void setBorrowerRating(Float rating) {
        this.borrowerRating = rating;
    }

    /**
     * set the list of array list of books the borrower has requested
     * @param list
     */

    public void setRequestedBookList(ArrayList<book> list) {
        requestedBookList = list;
    }

    /**
     * set the list of the books the borrower has borrowed
     * @param list
     */

    public void setAcceptedBookList(ArrayList<book> list) {
        acceptedBookList = list;
    }

    /**
     * set the list of the books the borrower has borrowed
     * @param list
     */

    public void setBorrowedBook(ArrayList<book> list) {
        borrowedBook = list;
    }

    /**
     * return the list of books the borrower has borrowed so far
     * @return list
     */

    public ArrayList<book> getBorrowedBook() {
        return borrowedBook;
    }

    /**
     * return the list of books the borrower has requested
     * @return
     */

    public ArrayList<book> getAcceptedBookList() {
        return acceptedBookList;
    }

    /**
     * return the list of books the borrower has requested
     * @return
     */

    public ArrayList<book> getRequestedBookList() {
        return requestedBookList;
    }

    /**
     * add the book the borrower has borrowed to the list
     * @param book
     */

    public void addBorrowedBook(book book) {
        borrowedBook.add(book);
    }
    /**
     * add the book the borrower has requested to the list
     * @param book
     */

    public void addRequestedBook(book book) {
        requestedBookList.add(book);
    }

    /**
     * add the book the borrower has requested to the list
     * @param book
     */

    public void addAcceptedBook(book book) {
        acceptedBookList.add(book);
    }

    /**
     * remove the book the borrower has requested from the list
     * @param book
     */

    public void deleteRequestedBook(book book) {
        requestedBookList.remove(book);
    }

    /**
     * remove the book the borrower has borrowed from the list
     * @param book
     */

    public void deleteBorrowedBook(book book) {
        borrowedBook.remove(book);
    }

    /**
     * return the rating of the borrower
     * @return
     */

    public float getBorrowerRating() {
        return borrowerRating;
    }

    /**
     * create a static instance of borrower, singleton pattern has implemented
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
