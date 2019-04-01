/*
 * Class MapActivityOwnerSetLocation.java
 *
 * Version 2.0
 *
 * Date 2019.4.1
 *
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
/**
 * allow owner to view his/her book, user can add book here
 * and user can filter books status by 4 button
 * @version 1.0
 *
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

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
 * this activity shows the books the user owns with different kinds of starus: requested, avaliable,
 * borrowed and accepted
 */
public class MyBookList extends AppCompatActivity {
    private ListView myBookList;
    private ArrayList<book> bookList = new ArrayList<>();
    private bookAdapter myBookAdapter;
    private bookAdapter availableBookAdapter;
    private bookAdapter requestedBookAdapter;
    private bookAdapter borrowedBookAdapter;
    private bookAdapter acceptedBookAdapter;
    private ArrayList<book> requestedBookList = new ArrayList<>();
    private ArrayList<book> acceptedBookList = new ArrayList<>();
    private ArrayList<book> borrowedBookList = new ArrayList<>();
    private ArrayList<book> availableBookList = new ArrayList<>();
    private ArrayList<String> booksID;
    private int yourChoice;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference DbRef = database.getReference();
    private FirebaseAuth auth;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    StorageReference storageRef = storage.getReference();
    //private book targetBook;
    private ArrayList<book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_list);





        ImageButton addBook = findViewById(R.id.addBook);

        myBookList = (ListView) findViewById(R.id.BookList);
        Button filter = findViewById(R.id.allButton);
        //final List<String> booksID = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();
        booksID = new ArrayList<>();
        DatabaseReference rootRef = database.getReference("lenders").child(uid).child("MyBookList");


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookList.clear();


                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    final String bookID = ds.getKey();
                    Log.i("bookID","hello"+bookID);
                    DbRef = database.getReference("book/"+bookID);
                    ValueEventListener eventListener1 = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {

                            book book1 = dataSnapshot1.getValue(book.class);
                            if (book1 != null) {
                                String name = book1.getName();
                                Log.i("test22", name);
                                final book targetBook = dataSnapshot1.getValue(book.class);
                                // Log.i("testName",targetBook.getName());
                                StorageReference imageRef = storageRef.child("book/" + bookID + "/1.jpg");
                                final long ONE_MEGABYTE = 10 * 1024 * 1024;
                                imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        //Log.i("step", "success1");
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        targetBook.setImage(bitmap);
                                        //Log.i("testName1", targetBook.getName());
                                        //bookList.add(targetBook);
                                        myBookAdapter.notifyDataSetChanged();

                                        //Log.i("size0000",Integer.toString(bookList.size()));
                                    }
                                    //........................................
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.i("Result", "failed");

                                    }
                                });


                                //................................



                                //Log.i("testName2",targetBook.getName());
                                //myBookAdapter.notifyDataSetChanged();
                                bookList.add(targetBook);
                                //myBookAdapter = new bookAdapter(MyBookList.this,bookList);

                                //myBookList.setAdapter(myBookAdapter);
                                myBookAdapter.notifyDataSetChanged();
                                books = bookList;
                                //if (targetBook.getImage()!=null) {
                                Log.i("step", "3");
                                //}

                            }}

                            @Override
                            public void onCancelled (@NonNull DatabaseError databaseError1){

                            }

                        };

                    DbRef.addValueEventListener(eventListener1);


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        Log.i("testnn","444");

        rootRef.addListenerForSingleValueEvent(eventListener);

        Log.i("testnn","555");
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyBookList.this, EditBookDetail.class);
                intent.putExtra("0", "0");
                Log.i("testlala","555");
                startActivity(intent);


            }
        });


        myBookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Implement this method
               // book bookItem = bookList.get(position);
                book bookItem = books.get(position);
                String bookId = bookItem.getID();
                Intent intent = new Intent(MyBookList.this, PrivateBookDetails.class);
                intent.putExtra("Id", bookId);
                intent.putExtra("flag","MyBooks");
                startActivity(intent);
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleChoiceDialog();
            }
        });


                Log.i("step","1");
        bookList = new ArrayList<>();
        myBookAdapter = new bookAdapter(this, bookList);
        myBookList.setAdapter(myBookAdapter);
        Log.i("step","2");

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(MyBookList.this,OwnerHomeActivity.class);
        startActivity(intent);
    }

    private void showSingleChoiceDialog() {
        final String[] items = {"available", "requested", "accepted", "borrowed", "show all"};
        yourChoice = -1;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(MyBookList.this);
        singleChoiceDialog.setTitle("Filter By");
        singleChoiceDialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                yourChoice = which;
            }
        });
        singleChoiceDialog.setPositiveButton("Next",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("yourChoice","hahaaa");
                        if(yourChoice == 0) {
                            Log.i("yourChoice","0");
                            availableBookList = new ArrayList<>();
                            for (book bookItem : bookList) {
                                if (bookItem.getStatus().equals("available")) {
                                    availableBookList.add(bookItem);
                                }
                            }
                            availableBookAdapter = new bookAdapter(MyBookList.this, availableBookList);
                            myBookList.setAdapter(availableBookAdapter);
                            books = availableBookList;
                            Toast.makeText(MyBookList.this, "Filtered by " + items[yourChoice], Toast.LENGTH_SHORT).show();
                        } else if(yourChoice == 1) {
                            Log.i("yourChoice","1");
                            requestedBookList = new ArrayList<>();
                            for (book bookItem : bookList) {
                                if (bookItem.getStatus().equals("requested")) {
                                    requestedBookList.add(bookItem);
                                }
                            }
                            requestedBookAdapter = new bookAdapter(MyBookList.this, requestedBookList);
                            myBookList.setAdapter(requestedBookAdapter);
                            books = requestedBookList;
                        } else if(yourChoice == 2) {
                            Log.i("yourChoice","2");
                            acceptedBookList = new ArrayList<>();
                            for (book bookItem : bookList) {
                                if (bookItem.getStatus().equals("accepted")) {
                                    acceptedBookList.add(bookItem);

                                }
                            }
                            acceptedBookAdapter = new bookAdapter(MyBookList.this, acceptedBookList);
                            myBookList.setAdapter(acceptedBookAdapter);
                            books = acceptedBookList;
                        } else if(yourChoice == 3) {
                            Log.i("yourChoice","3");
                            borrowedBookList = new ArrayList<>();
                            for (book bookItem : bookList) {
                                if (bookItem.getStatus().equals("borrowed")) {
                                    borrowedBookList.add(bookItem);
                                }
                            }
                            borrowedBookAdapter = new bookAdapter(MyBookList.this, borrowedBookList);
                            myBookList.setAdapter(borrowedBookAdapter);
                            books = borrowedBookList;
                        } else if(yourChoice == 4) {
                            Log.i("yourChoice","4");
                            myBookAdapter = new bookAdapter(MyBookList.this, bookList);
                            myBookList.setAdapter(myBookAdapter);
                            books = bookList;
                            Log.i("step","4");
                        } else{
                            Log.i("yourChoice","hello"+Integer.toString(yourChoice));
                        }

                    }
                });
        singleChoiceDialog.show();
    }

}