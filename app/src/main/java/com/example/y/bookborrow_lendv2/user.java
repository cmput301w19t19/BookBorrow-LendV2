package com.example.y.bookborrow_lendv2;

import android.media.Image;
import android.provider.ContactsContract;


public abstract class user {
    private String name;
    private Image photo;
    private String password;
    private Integer phone;
    private String email;
    /** A constructor with no parameters*/
    user(){}

    /** another constructoer with parameters*/
    user(String name, Image photo, String password, Integer phone, String email){
        this.name = name;
        this.photo = photo;
        this.password =password;
        this.phone = phone;
        this.email = email;
    }


    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public void setPhone(Integer phone) {
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

    public Integer getPhone() {
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
}

