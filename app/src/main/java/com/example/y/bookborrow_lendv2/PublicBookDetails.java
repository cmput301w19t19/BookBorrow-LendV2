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

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PublicBookDetails extends AppCompatActivity {


    private String bookid;
    private String flag;
    private TextView bookNameTV;
    private TextView ISBNTV;
    private TextView bookAuthorTV;
    private TextView bookStateTV;
    private TextView bookRateTV;
    private TextView bookOwnerTV;
    private TextView bookDescriptionTV;
    private Button requestButton;
    private Button returnButton;
    private book b;
    private String Keyword;
    private FirebaseAuth auth;
    private DatabaseReference r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_book_details);

        Intent intent = getIntent();
        bookid = intent.getStringExtra("Id");
        Keyword = intent.getStringExtra("Keyword");
        flag = intent.getStringExtra("flag");



        bookNameTV = (TextView)findViewById(R.id.puBookName);
        ISBNTV = (TextView)findViewById(R.id.puBookISBN);
        bookAuthorTV = (TextView)findViewById(R.id.puBookAuthor);
        bookStateTV = (TextView)findViewById(R.id.puBookState);
        bookRateTV = (TextView)findViewById(R.id.puBookRate);
        bookOwnerTV = (TextView)findViewById(R.id.puBookOwner);
        bookDescriptionTV = (TextView)findViewById(R.id.puBookDescription);

        requestButton = (Button)findViewById(R.id.requestTheBook);
        returnButton = (Button)findViewById(R.id.puReturnButton);

        FirebaseDatabase m = FirebaseDatabase.getInstance();
        //bookid = "45d11887-6961-41c0-916b-92b78c68dead"; ///for testing
        r = m.getReference("book/"+bookid);


        ValueEventListener bookListner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    b = dataSnapshot.getValue(book.class);
                    Log.i("test22",b.getName());
                    bookNameTV.setText(b.getName());

                    String ISBN = b.getISBN();
                    if (ISBN != null) {
                        ISBNTV.setText(ISBN);
                    }

                    String author = b.getAuthor();
                    if (author != null) {
                        bookAuthorTV.setText(author);
                    }

                    String state = b.getStatus();
                    if (state != null) {
                        bookStateTV.setText(state);
                    }

                    Double rate = b.getBookRating();
                    String srate = Double.toString(rate);
                    bookRateTV.setText(srate);

                    String description = b.getDescription();
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
        r.addValueEventListener(bookListner);

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get id

                auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                DatabaseReference r2 = FirebaseDatabase.getInstance().getReference();
                String uid = user.getUid();
                r2.child("book").child(bookid).child("requestList").child(uid).setValue(true);
                r2.child("borrowers").child(uid).child("requestList").child(bookid).setValue(true);
                r2.child("book").child(bookid).child("status").setValue("requested");
                //set book status to requested
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag.equals("searchbook")) {

                    Intent back = new Intent(PublicBookDetails.this, SearchResultForBook.class);
                    back.putExtra("key",Keyword);
                    startActivity(back);
                } else {
                    //setResult(1,back);

                    finish();
               }
            }
        });


    }
}
