package com.example.y.bookborrow_lendv2;

import android.media.Image;
import android.provider.ContactsContract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public abstract class User {
    private String name;
    private Image photo;
    private String password;
    private String phone;
    private String email;
    private String Uid;



    /** A constructor with no parameters*/
    User(){}

    /** another constructoer with parameters*/
    User(String name, Image photo, String password, String phone, String email){
        this.name = name;
        this.photo = photo;
        this.password =password;
        this.phone = phone;
        this.email = email;
    }


    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public Image getPhoto() {
        return photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(String id){this.Uid = id;}

    public String getUid(){
        return this.Uid;
    }







}

