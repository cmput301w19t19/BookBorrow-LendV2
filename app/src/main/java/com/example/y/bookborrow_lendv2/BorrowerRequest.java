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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Borrower Request activity
 * when user login in as a borrower, there is a bookRequested button
 * and this class be able to let user to view the book he/she requested, and the accepted books
 * @VERSION 2.0
 * @SEE PublicBookDetail;
 *
 */
public class BorrowerRequest extends AppCompatActivity {

    //private ArrayList<book> mainBookList = new ArrayList<book>();
    private ListView borrowerRequestbookList;
    private ArrayList<book> defaultBookList = new ArrayList<>();
    private ArrayList<book> requestedBookList = new ArrayList<>();
    private ArrayList<book> acceptedBookList = new ArrayList<>();
    private ArrayList<String> booksID;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference DbRef = database.getReference();

    private FirebaseAuth auth;
    private FirebaseUser user;
    private BorrowerRequestAdapter myBookAdapter;
    private BorrowerRequestAdapter requestAdapter;
    private BorrowerRequestAdapter acceptAdapter;
    private DatabaseReference rootRefR;
    private DatabaseReference rootRefA;
    private String message;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_request);


        Button showAccepted = (Button) findViewById(R.id.showAcceptButton);
        Button showRequested = (Button) findViewById(R.id.showRequestButton);

        borrowerRequestbookList = (ListView) findViewById(R.id.borrowerRequest);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //Log.i("Info","I am here");
        String uid = user.getUid();

        booksID = new ArrayList<>();
        rootRefR = database.getReference("borrowers").child(uid).child("requestList");
        rootRefA = database.getReference("borrowers").child(uid).child("AcceptedList");


        // user click showaccept button, book change to accepted books
        showAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "accepted";
                acceptedBookList = new ArrayList<>();
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        acceptedBookList.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            final String bookID = ds.getKey();
                            DbRef = database.getReference("book/"+bookID);
                            ValueEventListener eventListener1 = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                    final book targetBook = dataSnapshot1.getValue(book.class);
                                    if (targetBook != null) {
                                        StorageReference imageRef = storageRef.child("book/" + bookID + "/1.jpg");
                                        final long ONE_MEGABYTE = 10 * 1024 * 1024;
                                        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                Log.i("step", "success1");
                                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                targetBook.setImage(bitmap);
                                                Log.i("testName1", targetBook.getName());
                                                acceptAdapter.notifyDataSetChanged();
                                                //bookPhoto.setImageBitmap(bitmap);
                                            }

                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.i("Result", "failed");
                                            }
                                        });
                                        acceptedBookList.add(targetBook);
                                        acceptAdapter.notifyDataSetChanged();
                                        Log.i("acceptListDataChange", String.valueOf(acceptedBookList.size()));
                                    }
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
                rootRefA.addListenerForSingleValueEvent(eventListener);
                //defaultBookList = acceptedBookList;

                acceptAdapter = new BorrowerRequestAdapter(BorrowerRequest.this, acceptedBookList);
                //acceptAdapter = new BorrowerRequestAdapter(BorrowerRequest.this,acceptedBookList);
                borrowerRequestbookList.setAdapter(acceptAdapter);

            }
        });


        //set click on the main listView
        borrowerRequestbookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Implement this method
                if(message.equals("accepted")){
                    book bookItem = acceptedBookList.get(position);
                    String bookId = bookItem.getID();
                    Intent intent = new Intent(BorrowerRequest.this, PublicBookDetails.class);
                    intent.putExtra("Id", bookId);
                    intent.putExtra("flag","BorrowerRequest");
                    startActivity(intent);
                }
                else if(message.equals("requested")){
                    book bookItem = requestedBookList.get(position);
                    String bookId = bookItem.getID();
                    Intent intent = new Intent(BorrowerRequest.this, PublicBookDetails.class);
                    intent.putExtra("Id", bookId);
                    intent.putExtra("flag","BorrowerRequest");
                    startActivity(intent);
                }
            }
        });

        //button for show all request
        showRequested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "requested";
                requestedBookList = new ArrayList<>();
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        requestedBookList.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            final String bookID1 = ds.getKey();
                            DbRef = database.getReference("book/"+bookID1);
                            ValueEventListener eventListener1 = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                    final book targetBook1 = dataSnapshot1.getValue(book.class);
                                    if (targetBook1 != null) {
                                        StorageReference imageRef = storageRef.child("book/" + bookID1 + "/1.jpg");
                                        final long ONE_MEGABYTE = 1024 * 1024;
                                        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                Log.i("step", "success1");
                                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                targetBook1.setImage(bitmap);
                                                Log.i("testName1", targetBook1.getName());
                                                requestAdapter.notifyDataSetChanged();
                                                //bookPhoto.setImageBitmap(bitmap);
                                            }

                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.i("Result", "failed");
                                            }
                                        });
                                        Log.i("testName2", targetBook1.getName());
                                        //myBookAdapter.notifyDataSetChanged();
                                        requestedBookList.add(targetBook1);
                                        requestAdapter.notifyDataSetChanged();
                                        Log.i("requestListDataChange", String.valueOf(requestedBookList.size()));
                                    }
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
                rootRefR.addListenerForSingleValueEvent(eventListener);
                defaultBookList = requestedBookList;

                requestAdapter = new BorrowerRequestAdapter(BorrowerRequest.this, requestedBookList);
                //requestAdapter = new BorrowerRequestAdapter(BorrowerRequest.this,requestedBookList);
                borrowerRequestbookList.setAdapter(requestAdapter);

            }
        });


    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(BorrowerRequest.this,BorrowerMenu.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode ,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==0&&resultCode==1){
            String bookID = data.getStringExtra("ID");

            DbRef = database.getReference("book/"+bookID);
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    book targetBook = dataSnapshot.getValue(book.class);
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


}