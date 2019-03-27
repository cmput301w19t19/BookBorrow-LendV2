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
 * the books which a borrower is borrowing
 *
 * @author BoweiLi
 * @version 1.0
 */
public class BorrowBookList extends AppCompatActivity {
    private ListView myBorrowBookList;
    private ArrayList<book> borrowedBooks = new ArrayList<>();
    private BorrowingBookAdapter BorrowedBookAdapter;

    private FirebaseAuth auth;
    private FirebaseUser user;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    /**
     * The Db ref.
     */
    DatabaseReference dbRef = database.getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_book_list);
        myBorrowBookList = findViewById(R.id.BorrowBookList);

        myBorrowBookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * click on an item
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                book bookItem = borrowedBooks.get(position);
                String bookId = bookItem.getID();
                Intent intent = new Intent(BorrowBookList.this, PublicBookDetails.class);
                intent.putExtra("ID",bookId);
                startActivity(intent);

            }
        });



        borrowedBooks = new ArrayList<>();
        BorrowedBookAdapter = new BorrowingBookAdapter(this,borrowedBooks);
        myBorrowBookList.setAdapter(BorrowedBookAdapter);
        //String userID = "v1rSbJgp2uPgAxf4ZJXcclTgDyv2";
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //Log.i("Info","I am here");
        String userID = user.getUid();
        dbRef = database.getReference("borrowers").child(userID).child("BorrowBookList");
        ValueEventListener postListener = new ValueEventListener() {
            /**
             * get data from firebase
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    final String bookID = ds.getKey();
                    DatabaseReference DbRef = database.getReference("book/"+bookID);
                    ValueEventListener eventListener1 = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            final book targetBook = dataSnapshot1.getValue(book.class);
                            StorageReference imageRef = storageRef.child("book/"+bookID+"/1.jpg");
                            final long ONE_MEGABYTE = 10 * 1024 * 1024;
                            imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    Log.i("step","success1");
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                    targetBook.setImage(bitmap);
                                    //borrowedBooks.add(targetBook);
                                    BorrowedBookAdapter.notifyDataSetChanged();
                                    //bookPhoto.setImageBitmap(bitmap);
                                }

                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i("Result","failed");
                                }
                            });
                            borrowedBooks.add(targetBook);
                            BorrowedBookAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError1) {

                        }
                    };
                    DbRef.addValueEventListener(eventListener1);
                }
                /*borrower targetBorrower = dataSnapshot.getValue(borrower.class);
                borrowedBooks = targetBorrower.getBorrowedBook();
                BorrowedBookAdapter.notifyDataSetChanged();
                */
            }

            /**
             * get data failed
             * @param databaseError
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w( "loadPost:onCancelled", databaseError.toException());
            }
        };
        dbRef.addValueEventListener(postListener);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(BorrowBookList.this,BorrowerMenu.class);
        startActivity(intent);
    }
}
