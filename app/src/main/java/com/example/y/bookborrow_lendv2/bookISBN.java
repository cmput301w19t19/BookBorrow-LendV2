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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class bookISBN {
    private String ISBN;
    private Double totalRate = 0.00;
    private Integer borrowTime = 0;
    private ArrayList<RatingAndComment> commentList;
    private FirebaseDatabase m;
    private DatabaseReference r;


    bookISBN(String ISBN){
        this.ISBN = ISBN;
    }

    public void updateBorrowTime(){
        this.borrowTime += 1;
    }

    public void setBookRate(Double rating) { this.totalRate += rating; }

    public String getBookRate() {
        if (this.totalRate == 0.00){
            return "No one rate it yet!";
        }
        return Double.toString(this.totalRate/this.borrowTime);
    }

    public void setToFirebse(){
        m = FirebaseDatabase.getInstance();
        r = m.getReference("bookISBN/"+this.ISBN);
        r.child("totalRate").setValue(this.totalRate);
        r.child("borrowTime").setValue(this.borrowTime);
        r.child("RatingAndComment").setValue(this.commentList);
    }

    public void addNewRatingAndComment(RatingAndComment newComment){
        commentList.add(newComment);
        m = FirebaseDatabase.getInstance();
        r = m.getReference("book/"+this.ISBN+"/RatingAndComment");
        r.child("RatingAndComment").setValue(newComment);
    }



}
