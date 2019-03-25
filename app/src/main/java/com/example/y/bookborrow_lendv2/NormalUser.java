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

/**
 * the class that extends user so that we can get an instance of user
 *
 */

public class NormalUser extends user {
    private static NormalUser instance;

    /**
     * a constructor with parameters
     * @param name
     * @param photo
     * @param password
     * @param phone
     * @param email
     */

    public NormalUser(String name, Bitmap photo, String password, String phone, String email){
        super(name, photo, password, phone, email);
    }

    /**
     * a constructor with no parameters
     */


    public NormalUser(){
        super();
    }

    /**
     * create and return a static instance of normal user
     * @return a instance of normal user
     */

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
