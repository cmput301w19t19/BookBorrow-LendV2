/*
 * Class user.java
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

import android.graphics.Bitmap;
import android.media.Image;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * lender object  abstract class
 * contain all user related attributes
 * setter and getter for the book info.
 * And firebase related methods include:
 * setToFirebase , deleteFromFirebase
 * <p>
 * <p>
 * Created  on 2/15/19.
 *
 * @see borrower
 * @see lender
 * @since 1.0
 */
public abstract class user {
    private String name;
    private Bitmap photo;
    private String password;
    private String phone;
    private String email;
    private String Uid;



    /**
     * A constructor with no parameters
     */
    user(){}


    /**
     * another constructor with parameters @param name the name
     *
     * @param name     the name
     * @param photo    the photo
     * @param password the password
     * @param phone    the phone
     * @param email    the email
     */
    user(String name, Bitmap photo, String password, String phone, String email){


        this.name = name;
        this.photo = photo;
        this.password =password;
        this.phone = phone;
        this.email = email;
    }


    /**
     * set profile image of the user
     *
     * @param photo the photo
     */


    public void setPhoto(Bitmap photo) {

        this.photo = photo;
    }

    public void setToFirebase(){
        FirebaseDatabase m = FirebaseDatabase.getInstance();
        DatabaseReference r = m.getReference("users/"+this.Uid);
        r.child("name").setValue(this.name);
        r.child("password").setValue(this.password);
        r.child("phone").setValue(this.phone);
    }

    /**
     * set phone of the user
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * set the login password of the user
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * set the user name of the user
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * return the account password of the user
     *
     * @return password password
     */
    public String getPassword() {
        return password;
    }

    /**
     * return the phone number of the user
     *
     * @return phone phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * return the user name of the user
     *
     * @return name name
     */
    public String getName() {
        return name;
    }

    /**
     * return the profiole photo of the user
     *
     * @return photo photo
     */

    public Bitmap getPhoto() {

        return photo;
    }

    /**
     * return the rmail of the user
     *
     * @return user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * set the emai of the user
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * set the unique id of user
     *
     * @param id the id
     */
    public void setUid(String id){this.Uid = id;}

    /**
     * return the id of the user
     *
     * @return Uid string
     */
    public String getUid(){
        return this.Uid;
    }







}

