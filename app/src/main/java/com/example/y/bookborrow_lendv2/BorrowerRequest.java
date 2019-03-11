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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BorrowerRequest extends AppCompatActivity {

    //private ArrayList<Book> mainBookList = new ArrayList<Book>();
    private ListView borrowerRequestbookList;
    private ArrayList<Book> defaultBookList = new ArrayList<>();
    private ArrayList<Book> requestedBookList = new ArrayList<>();
    private ArrayList<Book> acceptedBookList = new ArrayList<>();
    private ArrayList<String> booksID;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference DbRef = database.getReference();

    private FirebaseAuth auth;
    private FirebaseUser user;
    private BorrowerRequestAdapter myBookAdapter;
    private BorrowerRequestAdapter requestAdapter;
    private BorrowerRequestAdapter acceptAdapter;
    //private BorrowingBookAdapter myBookAdapter;
    //private BorrowingBookAdapter requestAdapter;
    ///private BorrowingBookAdapter acceptAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_request);


        Button showAccepted = (Button) findViewById(R.id.showAcceptButton);
        Button showRequested = (Button) findViewById(R.id.showRequestButton);

        borrowerRequestbookList = (ListView) findViewById(R.id.borrowerRequest);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        Log.i("Info","I am here");
        String uid = user.getUid();

        booksID = new ArrayList<>();
        DatabaseReference rootRef = database.getReference("borrowers").child(uid).child("BorrowerRequest");
        //DatabaseReference rootRef = database.getReference("Lender").child(uid).child("MyBookList");




        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String bookID = ds.getKey();
                    DbRef = database.getReference("Book/"+bookID);
                    ValueEventListener eventListener1 = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            Book targetBook = dataSnapshot1.getValue(Book.class);
                            defaultBookList.add(targetBook);
                            myBookAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError1) {

                        }
                    };
                    DbRef.addValueEventListener(eventListener1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        rootRef.addListenerForSingleValueEvent(eventListener);


        //
        // User click showaccept button, Book cahnge to accepted books
        showAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptedBookList = new ArrayList<>();

                for (Book bookItem : defaultBookList) {

                    if (bookItem.getStatus().equals("Accepted")) {
                        acceptedBookList.add(bookItem);

                    }
                }
                acceptAdapter = new com.example.y.bookborrow_lendv2.BorrowerRequestAdapter(BorrowerRequest.this, acceptedBookList);
                //acceptAdapter = new BorrowingBookAdapter(BorrowerRequest.this,acceptedBookList);
                borrowerRequestbookList.setAdapter(acceptAdapter);

            }
        });


        //set click on the main listView
        borrowerRequestbookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Implement this method
                Book bookItem = defaultBookList.get(position);
                String bookId = bookItem.getID();
                Intent intent = new Intent(BorrowerRequest.this, PublicBookDetails.class);
                intent.putExtra("Id", bookId);
                startActivity(intent);
            }
        });

        //button for ashow all request
        showRequested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestedBookList = new ArrayList<>();
                for (Book bookItem : defaultBookList) {
                    if (bookItem.getStatus().equals("Requested")) {
                        requestedBookList.add(bookItem);
                    }
                }
                requestAdapter = new com.example.y.bookborrow_lendv2.BorrowerRequestAdapter(BorrowerRequest.this, requestedBookList);
                //requestAdapter = new BorrowingBookAdapter(BorrowerRequest.this,requestedBookList);
                borrowerRequestbookList.setAdapter(requestAdapter);

            }
        });

        //these three lines what to do?
        defaultBookList = new ArrayList<>();
        myBookAdapter = new com.example.y.bookborrow_lendv2.BorrowerRequestAdapter(this, defaultBookList);
        //myBookAdapter = new BorrowingBookAdapter(this,defaultBookList);
        borrowerRequestbookList.setAdapter(myBookAdapter);


    }

    //confused by these lines??
    @Override
    protected void onActivityResult(int requestCode,int resultCode ,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==0&&resultCode==1){
            String bookID = data.getStringExtra("ID");

            DbRef = database.getReference("Book/"+bookID);
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Book targetBook = dataSnapshot.getValue(Book.class);
                    defaultBookList.add(targetBook);
                    myBookAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w( "loadPost:onCancelled", databaseError.toException());
                }
            };
            DbRef.addValueEventListener(postListener);

        }

    }

    public void showAcceptRequest(){

    }

    public void showWatchList(){

    }
}
