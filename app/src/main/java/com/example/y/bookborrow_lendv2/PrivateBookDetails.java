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

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * This Class is to show all the detail of a book to the book owner
 * @author team 19
 * @see MyBookList
 * @version1.0
 */
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
    Button returnButton;
    book bookx;
    FirebaseAuth auth;
    Button locationButton;

    private String locationCode;

    /**function that user click location button and jump to map activity
    public void toOwnerMapActivity(View view){

        Intent intent = new Intent(getApplicationContext(), MapsActivityOwnerSetLocation.class);
        startActivity(intent);

        intent.putExtra("bookid",bookid);
    }*/






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_book_details);

        Intent intent = getIntent();
        bookid = intent.getStringExtra("Id");
        bookNameTV = (TextView)findViewById(R.id.pBookName);
        ISBNTV = (TextView)findViewById(R.id.pBookISBN);
        bookAuthorTV = (TextView)findViewById(R.id.pBookAuthor);
        bookStateTV = (TextView)findViewById(R.id.pBookState);
        bookRateTV = (TextView)findViewById(R.id.pBookRate);
        bookDescriptionTV = (TextView)findViewById(R.id.pBookDescription);
        deleteButton = (Button)findViewById(R.id.BookDetailDelete);
        editButton = (Button)findViewById(R.id.bookDetailEdit);
        requestButton = (Button)findViewById(R.id.bookDetailRequest);
        returnButton = (Button)findViewById(R.id.ReturnButton);
        locationButton = (Button)findViewById(R.id.pBookLocation);

        /**
         *  Get the information of the book from firebase and show them on the screen
         */
        FirebaseDatabase m = FirebaseDatabase.getInstance();
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

                    String rate = bookx.getBookRating();
                    bookRateTV.setText(rate);

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





        /**
         * Prompt the user to edit book detail if the user want
         * Go to editBookDetail
         * @see editBookDetail
         */
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PrivateBookDetails.this,EditBookDetail.class);
                i.putExtra("0",bookid);
                startActivityForResult(i,2);
            }
        });

        /**
         *Prompt the user to delete the book if the user does not want to share it
         * remove the book from the book list on firebase
         * remove the book from the owner book list firebase
         */
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookx.deleteFromFirebase();
                auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                DatabaseReference r = FirebaseDatabase.getInstance().getReference();
                String uid = user.getUid();
                r.child("lenders").child(uid).child("MyBookList").child(bookid).setValue(null,null);
                Intent i = new Intent(PrivateBookDetails.this,MyBookList.class);
                startActivity(i);

            }
        });

        /**
         * Prompt the user to see all the requests on the book
         * go to requestToOwner activity
         * @see requestToOwner
         */
        requestButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(PrivateBookDetails.this,RequestToOwner.class);
                i.putExtra("request",bookid);
                startActivityForResult(i,3);
            }
        });

        /**
         * Return to the activity which all this one, using flag to distinct caller
         */
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PrivateBookDetails.this,MyBookList.class);
                startActivity(i);
            }
        });



        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(getApplicationContext(), MapsActivityOwnerSetLocation.class);
                //startActivityForResult(new Intent(PrivateBookDetails.this, MapsActivityOwnerSetLocation.class), 4);
                pickPointOnMap();



            }
        });
    }

    //switch to map activity, user can pick a point on map
    //map activity will return lat and long
    static final int pickMapPointRequest = 100;
    private void pickPointOnMap(){
        Intent pickPointIntent = new Intent(this,MapsActivityOwnerSetLocation.class);
        startActivityForResult(pickPointIntent, pickMapPointRequest);
    }


    /**
     *  Get all result back
     * @param requestCode
     * @param resultCode
     * @param Data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data){
        super.onActivityResult(requestCode,resultCode,Data);
        if (requestCode == 2 && resultCode == 1){
            bookid = Data.getStringExtra("ID");
        }

        if (requestCode == 3){
        }


        //return from map activity and
        if (requestCode == pickMapPointRequest){


                LatLng latLng = (LatLng) Data.getParcelableExtra("picked_point");
                Toast.makeText(this, "Point Chosen: " + latLng.latitude + " " + latLng.longitude, Toast.LENGTH_LONG).show();
                //locationCode = Data.getStringExtra("location");
                //Log.i("location", locationCode);
                //Toast.makeText(getApplicationContext(),"location back",Toast.LENGTH_SHORT).show();





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

                String rate = bookx.getBookRating();
                bookRateTV.setText(rate);

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
