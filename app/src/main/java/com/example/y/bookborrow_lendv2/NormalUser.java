package com.example.y.bookborrow_lendv2;

import android.media.Image;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NormalUser extends user {




    public NormalUser(String name, Image photo, String password, Integer phone, String email){
        super(name, photo, password, phone, email);
    }


    public NormalUser(){
        super();
    }



}
