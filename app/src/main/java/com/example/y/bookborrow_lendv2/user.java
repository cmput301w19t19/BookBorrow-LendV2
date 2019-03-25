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

import android.graphics.Bitmap;
import android.media.Image;
import android.provider.ContactsContract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * lender object  abstract class
 * contain all user related attributes
 * setter and getter for the book info.
 * And firebase related methods include:
 *  setToFirebase , deleteFromFirebase
 *
 *
 *  Created  on 2/15/19.
 *  @since 1.0
 * @see  borrower
 * @see lender
 */

public abstract class user {
    private String name;
    private Bitmap photo;
    private String password;
    private String phone;
    private String email;
    private String Uid;




    /** A constructor with no parameters*/
    user(){}

    /** another constructoer with parameters*/
    user(String name, Bitmap photo, String password, String phone, String email){
        this.name = name;
        this.photo = photo;
        this.password =password;
        this.phone = phone;
        this.email = email;
    }


    /**
     * set profile image of the user
     * @param photo
     */

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    /**
     * set phone of the user
     * @param phone
     */

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * set the login password of the user
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * set the user name of the user
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * return the account password of the user
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * return the phone number of the user
     * @return phone
     */

    public String getPhone() {
        return phone;
    }

    /**
     * return the user name of the user
     * @return name
     */

    public String getName() {
        return name;
    }

    /**
     * return the profiole photo of the user
     * @return photo
     */

    public Bitmap getPhoto() {
        return photo;
    }

    /**
     * return the rmail of the user
     * @return user
     */

    public String getEmail() {
        return email;
    }

    /**
     * set the emai of the user
     * @param email
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * set the unique id of user
     * @param id
     */

    public void setUid(String id){this.Uid = id;}

    /**
     * return the id of the user
     * @return Uid
     */
    public String getUid(){
        return this.Uid;
    }







}

