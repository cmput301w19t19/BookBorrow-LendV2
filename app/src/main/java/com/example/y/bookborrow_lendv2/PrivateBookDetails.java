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
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PrivateBookDetails extends AppCompatActivity {

    String bookid;
    TextView bookNameTV;
    TextView ISBNTV;
    TextView bookAuthorTV;
    TextView bookStateTV;
    TextView bookRateTV;
    TextView bookDescriptionTV;
    Button deleteButton;
    Button editButton;
    Button requestButton;
    book bookx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_book_details);

        Intent intent = getIntent();
        bookid = intent.getDataString();

        bookNameTV = (TextView)findViewById(R.id.pBookName);
        ISBNTV = (TextView)findViewById(R.id.pBookISBN);
        bookAuthorTV = (TextView)findViewById(R.id.pBookAuthor);
        bookStateTV = (TextView)findViewById(R.id.pBookState);
        bookRateTV = (TextView)findViewById(R.id.pBookRate);
        bookDescriptionTV = (TextView)findViewById(R.id.pBookDescription);
        deleteButton = (Button)findViewById(R.id.BookDetailDelete);
        editButton = (Button)findViewById(R.id.bookDetailEdit);
        requestButton = (Button)findViewById(R.id.bookDetailRequest);


        /*
        book v = new book();
        v.setAuthor("Yizhou Wen");
        v.setISBN("778887787887887");
        v.setDescription("test for request list");
        v.setName("How to forget important things");
        v.setStatusToAccepted();
        v.addRequested("v1rSbJgp2uPgAxf4ZJXcclTgDyv2");
        v.setToFirebase();
        */



        FirebaseDatabase m = FirebaseDatabase.getInstance();
        bookid = "f0ae545f-58d4-4a33-9de7-eb761e621b5e"; ///for testing
        DatabaseReference r = m.getReference("book/"+bookid);
        ValueEventListener bookListner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    bookx = dataSnapshot.getValue(book.class);
                    bookNameTV.setText(bookx.getName());

                    String ISBN = bookx.getISBN();
                    if (ISBN != null) {
                        ISBNTV.setText(ISBN);
                    }

                    String author = bookx.getAuthor();
                    if (author != null) {
                        bookAuthorTV.setText(author);
                    }

                    String state = bookx.getStatus();
                    if (state != null) {
                        bookStateTV.setText(state);
                    }

                    Double rate = bookx.getBookRating();
                    String srate = Double.toString(rate);
                    bookRateTV.setText(srate);

                    String description = bookx.getDescription();
                    if (description != null) {
                        bookDescriptionTV.setText(description);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Fail to get data from database",Toast.LENGTH_SHORT).show();

            }
        };
        r.addListenerForSingleValueEvent(bookListner);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PrivateBookDetails.this,EditBookDetail.class);
                i.putExtra("0",bookid);
                startActivityForResult(i,2);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookx.deleteFromFirebase();
                finish();
            }

        });

        requestButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(PrivateBookDetails.this,RequestToOwner.class);
                i.putExtra("request",bookid);
                startActivityForResult(i,3);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data){
        super.onActivityResult(requestCode,resultCode,Data);
        if (requestCode == 2 && resultCode == 1){
            bookid = Data.getStringExtra("ID");
            Toast.makeText(getApplicationContext(),bookid,Toast.LENGTH_SHORT).show();
        }

        if (requestCode == 3){
            Toast.makeText(getApplicationContext(),"Return from FengYuan",Toast.LENGTH_SHORT).show();

        }


        FirebaseDatabase m = FirebaseDatabase.getInstance();
        DatabaseReference r = m.getReference("book/"+bookid);

        ValueEventListener bookListner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                bookx = dataSnapshot.getValue(book.class);

                String bookName;
                if (bookx.getName() != null){
                    bookNameTV.setText(bookx.getName());
                }
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

                Double rate = bookx.getBookRating();
                String srate = Double.toString(rate);
                bookRateTV.setText(srate);

                String description = bookx.getDescription();
                if (description != null){
                    bookDescriptionTV.setText(ISBN);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Fail to renew data",Toast.LENGTH_SHORT).show();

            }
        };

        r.addListenerForSingleValueEvent(bookListner);


    }
}
