package com.example.y.bookborrow_lendv2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

/**
 * This class is to provide an interface for owner to edit details of one of his book
 * @author Team 19
 * @see PrivateBookDetails
 * @version 1.0
 */

public class EditBookDetail extends AppCompatActivity {
    book b;
    EditText bookNamkeEditText;
    EditText authorEditText;
    EditText ISBNEditText;
    EditText descriptionEditText;
    String id;
    private FirebaseAuth auth;


    /**
     * Usage one:
     * Get a book id from PrivateBookDetails and show current information of a book to owner
     * Prompt owner to edit book information and save it
     * Usage two:
     * Get a flag (int 0) from MyBookList and prompt owner to enter information of a book
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book_detail);

        auth = FirebaseAuth.getInstance();
        Button saveButton = (Button)findViewById(R.id.Buttonsave);
        Button uploadButton = (Button)findViewById(R.id.ButtonUpload);

        bookNamkeEditText = (EditText)findViewById(R.id.pt4);
        authorEditText = (EditText)findViewById(R.id.pt2);
        ISBNEditText = (EditText)findViewById(R.id.pt3);
        descriptionEditText = (EditText)findViewById(R.id.editTextDes);

        Intent i = getIntent();
        id = i.getStringExtra("0");

        if (id.equals("0")){
            b = new book();
            id = b.getID();
        }else{

            FirebaseDatabase m = FirebaseDatabase.getInstance();
            DatabaseReference r = m.getReference("book/"+id);

            ValueEventListener bookLister = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        b = dataSnapshot.getValue(book.class);
                        b.setID(id);

                        String bookName = b.getName();
                        if (bookName != null){
                            bookNamkeEditText.setText(b.getName());
                        }

                        String ISBN = b.getISBN();
                        if (ISBN != null) {
                            ISBNEditText.setText(b.getISBN());
                        }

                        String author = b.getAuthor();
                        if (author != null) {
                            authorEditText.setText(b.getAuthor());
                        }

                        String description = b.getDescription();
                        if (description != null) {
                            descriptionEditText.setText(b.getDescription());
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"Fail to get data from database",Toast.LENGTH_SHORT).show();
                }
            };
            r.addListenerForSingleValueEvent(bookLister);

        }

        /**
         * get all the information from users
         * update book infor to firebase, path:booklendborrow/book/[bookid]
         * update owner book list to firebase, path:booklendborrow/[user id]
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.setName(bookNamkeEditText.getText().toString());
                b.setAuthor(authorEditText.getText().toString());
                b.setDescription(descriptionEditText.getText().toString());
                b.setISBN(ISBNEditText.getText().toString());

                bookISBN ISBN = new bookISBN(ISBNEditText.getText().toString());
                ISBN.setToFirebse();


                FirebaseUser user = auth.getCurrentUser();
                DatabaseReference r = FirebaseDatabase.getInstance().getReference();

                String uid = user.getUid();
                b.setOwnerID(uid);
                r.child("lenders").child(uid).child("MyBookList").child(id).setValue(true);

                b.setToFirebase();
                String bookid = b.getID();
                Intent back = new Intent();
                back.putExtra("ID",bookid);
                setResult(1,back);
                finish();
            }
        });

        /**
         * Go to upload image activity
         * will be finished in version2
         */

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upload = new Intent();
                String bookid = b.getID();
                upload.putExtra("bookid",bookid);
                Toast.makeText(getApplicationContext(),"The function is waiting for implemented",Toast.LENGTH_SHORT).show();
            }
        });


    }

}
