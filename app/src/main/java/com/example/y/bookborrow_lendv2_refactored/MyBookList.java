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
package com.example.y.bookborrow_lendv2_refactored;
/**
 * allow owner to view his/her book, user can add book here
 * and user can filter books status by 4 button
 * @version 1.0
 */

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
    /**
     * The Database.
     */
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    /**
     * The Db ref.
     */
    DatabaseReference DbRef = database.getReference();
    private FirebaseAuth auth;
    /**
     * The Storage.
     */
    FirebaseStorage storage = FirebaseStorage.getInstance();
    /**
     * The Storage ref.
     */
    StorageReference storageRef = storage.getReference();
    //private book targetBook;
    private ArrayList<book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_list);





        ImageButton addBook = findViewById(R.id.addBook);
        final Button available = (Button) findViewById(R.id.availableFilter);
        Button backButton = findViewById(R.id.backButton);
        Button requested = (Button) findViewById(R.id.requestedFilter);
        Button accepted = (Button) findViewById(R.id.acceptedFilter);
        Button borrowed = (Button) findViewById(R.id.borrowedFilter);
        Button all = (Button) findViewById(R.id.allButton);
        myBookList = (ListView) findViewById(R.id.BookList);
        //final List<String> booksID = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();
        booksID = new ArrayList<>();
        DatabaseReference rootRef = database.getReference("lenders").child(uid).child("MyBookList");

        Log.i("testnn","222");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.i("testnn","333");

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    final String bookID = ds.getKey();
                    Log.i("bookID",bookID);
                    DbRef = database.getReference("book/"+bookID);
                    ValueEventListener eventListener1 = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            Log.i("test22","hello");
                            final book targetBook = dataSnapshot1.getValue(book.class);
                            Log.i("testName",targetBook.getName());
                            StorageReference imageRef = storageRef.child("book/"+bookID+"/1.jpg");
                            final long ONE_MEGABYTE = 1024 * 1024;
                            imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    Log.i("step","success1");
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                    targetBook.setImage(bitmap);
                                    Log.i("testName1",targetBook.getName());
                                    myBookAdapter.notifyDataSetChanged();
                                    //bookPhoto.setImageBitmap(bitmap);
                                }

                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i("Result","failed");
                                }
                            });
                            bookList.add(targetBook);
                            Log.i("testName2",targetBook.getName());
                            myBookAdapter.notifyDataSetChanged();
                            //bookList.add(targetBook);
                            //myBookAdapter = new bookAdapter(MyBookList.this,bookList);

                            //myBookList.setAdapter(myBookAdapter);
                            //myBookAdapter.notifyDataSetChanged();
                            //if (targetBook.getImage()!=null) {
                                Log.i("step", "3");
                            //}
                            books = bookList;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError1) {

                        }
                    };
                    DbRef.addValueEventListener(eventListener1);

                    //booksID.add(book);
                    //boolean a = booksID.contains(book);
                    //Toast.makeText(MyBookList.this,book,Toast.LENGTH_SHORT).show();
                    //Log.i("testnn",Boolean.toString(a));
                }


                //Log.i("testsize3",Integer.toString(book.size()));
                //myBookAdapter = new bookAdapter(MyBookList.this, books);
                //myBookList.setAdapter(myBookAdapter);
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
                startActivityForResult(intent, 0);
            }
        });

        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                availableBookList = new ArrayList<>();
                for (book bookItem : bookList) {
                    //Toast.makeText(MyBookList.this,"boweiLi",Toast.LENGTH_SHORT).show();
                    if (bookItem.getStatus().equals("available")) {
                        availableBookList.add(bookItem);
                        //Integer
                        Toast.makeText(MyBookList.this, "hello", Toast.LENGTH_SHORT).show();
                    }
                }
                availableBookAdapter = new bookAdapter(MyBookList.this, availableBookList);
                myBookList.setAdapter(availableBookAdapter);
                books = availableBookList;

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyBookList.this,OwnerHomeActivity.class);
                startActivity(intent);
            }
        });

        requested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestedBookList = new ArrayList<>();
                for (book bookItem : bookList) {
                    if (bookItem.getStatus().equals("requested")) {
                        requestedBookList.add(bookItem);
                    }
                }
                requestedBookAdapter = new bookAdapter(MyBookList.this, requestedBookList);
                myBookList.setAdapter(requestedBookAdapter);
                books = requestedBookList;


            }
        });

        accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptedBookList = new ArrayList<>();
                for (book bookItem : bookList) {
                    if (bookItem.getStatus().equals("accepted")) {
                        acceptedBookList.add(bookItem);

                    }
                }
                acceptedBookAdapter = new bookAdapter(MyBookList.this, acceptedBookList);
                myBookList.setAdapter(acceptedBookAdapter);
                books = acceptedBookList;

            }
        });

        borrowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrowedBookList = new ArrayList<>();
                for (book bookItem : bookList) {
                    if (bookItem.getStatus().equals("borrowed")) {
                        borrowedBookList.add(bookItem);
                    }
                }
                borrowedBookAdapter = new bookAdapter(MyBookList.this, borrowedBookList);
                myBookList.setAdapter(borrowedBookAdapter);
                books = borrowedBookList;

            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBookAdapter = new bookAdapter(MyBookList.this, bookList);
                myBookList.setAdapter(myBookAdapter);
                Log.i("step","4");
            }
        });


        myBookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Implement this method
               // book bookItem = bookList.get(position);
                book bookItem = books.get(position);

                Toast.makeText(MyBookList.this,bookItem.getAuthor(),Toast.LENGTH_SHORT).show();
                String bookId = bookItem.getID();
                Intent intent = new Intent(MyBookList.this, PrivateBookDetails.class);
                intent.putExtra("Id", bookId);
                startActivityForResult(intent,2);
            }
        });


        Log.i("step","1");
        bookList = new ArrayList<>();
        myBookAdapter = new bookAdapter(this, bookList);
        myBookList.setAdapter(myBookAdapter);
        Log.i("step","2");

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode ,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==0&&resultCode==1){
            final String bookID1 = data.getStringExtra("ID");
            //String BookID = "0f45b9af-ebc7-4449-a6db-f88f9589a7c0";
            DbRef = database.getReference("book/"+bookID1);
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final book targetBook1 = dataSnapshot.getValue(book.class);
                    StorageReference imageRef = storageRef.child("book/"+bookID1+"/1.jpg");
                    final long ONE_MEGABYTE = 1024 * 1024;
                    imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Log.i("step","success1");
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            targetBook1.setImage(bitmap);
                            bookList.add(targetBook1);
                            myBookAdapter.notifyDataSetChanged();
                            //bookPhoto.setImageBitmap(bitmap);
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("Result","failed");
                        }
                    });
                    //bookList.add(targetBook);
                    //myBookAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w( "loadPost:onCancelled", databaseError.toException());
                }
            };
            DbRef.addValueEventListener(postListener);

        }
        else if (requestCode==2&&resultCode==RESULT_OK){
            Log.i("finish","true");
        }

    }

}