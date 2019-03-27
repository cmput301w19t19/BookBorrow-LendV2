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

/**
 * class defined as object in Request_Book_MyAdapter
 *
 * @author Yuan Feng
 */
public class comment {
    private String userName;
    private String rating;
    /**
     * The Selected.
     */
    private String comment;
    private Bitmap photo;

    /**
     * initialize the object
     *
     * @param userName the user name
     * @param rating   the rating
     * @param comment   the comment
     */
    public comment(String userName, String rating, String comment) {
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        //this.photo = photo;
    }


    /**
     * Get image bitmap.
     *
     * @return the bitmap
     */
    public Bitmap getImage(){
        return this.photo;
    }

    /**
     * get the username of the comment object
     *
     * @return username string
     */
    public String getUserName(){
        return userName;
    }

    /**
     * set the username of the comment object
     *
     * @param userName the user name
     */
    public void setUserName(String userName){
        this.userName = userName;
    }

    /**
     * get the rating of the B-comment object
     *
     * @return rating String
     */
    public String getRating(){
        return rating;
    }

    /**
     * get the object's userID
     *
     * @return userID string
     */
    public String getComment(){
        return this.comment;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    /**
     * set the object's userID
     *
     * @param comment the comments
     */


    public void setComment(String comment){
        this.comment = comment;
    }
}
