/*
 * Copyright 2019 TEAM01
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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PrivateBookDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_book_details);


        Intent intent = getIntent();

        //Bundle bundle = intent.getExtras();
        String bookName = intent.getDataString();
        book bookx = new book();


        TextView bookNameTV = (TextView)findViewById(R.id.pBookName);
        TextView ISBNTV = (TextView)findViewById(R.id.pBookISBN);
        TextView bookAuthorTV = (TextView)findViewById(R.id.pBookAuthor);
        TextView bookStateTV = (TextView)findViewById(R.id.pBookState);
        TextView bookRateTV = (TextView)findViewById(R.id.pBookRate);
        TextView bookDescriptionTV = (TextView)findViewById(R.id.pBookDescription);

        bookNameTV.setText(bookName);

        String ISBN = bookx.getISBN();
        if (ISBN != null){
            ISBNTV.setText(ISBN);
        }

        String author = bookx.getAuthor();
        if (author != null){
            bookAuthorTV.setText(ISBN);
        }

        String state = bookx.getStatus();
        if (state != null){
            bookStateTV.setText(state);
        }

        float rate = bookx.getBookRating();
        String srate = Float.toString(rate);
        bookRateTV.setText(srate);

        String description = bookx.getDescription();
        if (description != null){
            bookDescriptionTV.setText(ISBN);
        }

        Button deleteButton = (Button)findViewById(R.id.BookDetailDelete);
    }
}
