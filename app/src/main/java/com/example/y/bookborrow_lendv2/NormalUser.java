package com.example.y.bookborrow_lendv2;

import android.media.Image;

public class NormalUser extends User {
    private static NormalUser instance;



    public NormalUser(String name, Image photo, String password, String phone, String email){
        super(name, photo, password, phone, email);
    }


    public NormalUser(){
        super();
    }

    public static NormalUser Instance()
    {
        //if no instance is initialized yet then create new instance
        //else return stored instance
        if (instance == null)
        {
            instance = new NormalUser();
        }
        return instance;
    }




}
