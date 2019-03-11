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
 * lender object class extends user class
 * contain two ArrayList borrowedBook and requestedList
 *
 *
 *  Created  on 2/15/19.
 *  @since 1.0
 * @see user
 */
public class lender extends user {
    private float lenderRating;
    private ArrayList<String> lentBook = new ArrayList<>();
    private ArrayList<String> requestedBookList = new ArrayList<>();
    private ArrayList<String> MyBookList = new ArrayList<>();
    private static lender instance;


    lender() {
    }

    /**
     * set the rating of the lender
     * @param rating
     */
    public void setLenderRating(Float rating) {
        this.lenderRating = rating;
    }

    /**
     * return the rating of the lender
     * @return rating
     */

    public float getLenderRating() {
        return lenderRating;
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


    public static lender Instance()
    {
        //if no instance is initialized yet then create new instance
        //else return stored instance
        if (instance == null)
        {
            instance = new lender();
        }
        return instance;
    }


}

