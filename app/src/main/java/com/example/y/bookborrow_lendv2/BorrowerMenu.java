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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.readystatesoftware.viewbadger.BadgeView;

import java.util.ArrayList;

/**
 * main menu for user login as borrower,
 * user has four option , requested book, booklist. search for book and scan the book
 *
 * @author Team19
 * @version 1.0
 */


public class BorrowerMenu extends AppCompatActivity {
    //private ImageButton acceptedButton;
    private FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ArrayList<String> books = new ArrayList<>();

    private ImageButton NA1;
    private ImageButton NA2;
    private ImageButton NA3;
    private ImageButton NA4;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_menu);

        NA1 = (ImageButton)findViewById(R.id.NA21);
        NA2 = (ImageButton)findViewById(R.id.NA22);
        NA3 = (ImageButton)findViewById(R.id.NA23);
        NA4 = (ImageButton)findViewById(R.id.NA24);

        TextView requested = (TextView) findViewById(R.id.select_borrow_menu_1);
        ImageButton acceptedButton = (ImageButton) findViewById(R.id.newRequestButton);
        TextView bookList = (TextView) findViewById(R.id.select_borrow_menu_2);
        TextView search = (TextView) findViewById(R.id.select_borrow_menu_3);
        TextView scan = (TextView) findViewById(R.id.select_borrow_menu_4);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        final String uid = user.getUid();

        DatabaseReference rootRef = database.getReference("borrowers").child(uid).child("AcceptedRequests");
        Log.i("ttttttttt3",uid);

        //set badge view
        final BadgeView badge = new BadgeView(this,acceptedButton );


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    final String bookID = ds.getKey();


                    DatabaseReference dbRef = database.getReference("borrowers").child(uid).child("AcceptedRequests").child(bookID);
                    ValueEventListener eventListener2 = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                                if (ds1.getKey().equals("checkedByBorrower")) {
                                    if (ds1.getValue().equals(false)) {
                                        books.add(bookID);
                                        Log.i("popopopopo",Integer.toString(books.size()));
                                        //newRequestListSize = Integer.toString(BookList.size());




                                    }
                                }
                            }


                            if  (books.size() > 0){
                                Log.i("llllllll",Integer.toString(books.size()));

                                badge.setText(Integer.toString(books.size()));

                                badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);

                                //newRequestMessage.setVisibility(View.VISIBLE);

                                badge.show();}





                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    dbRef.addValueEventListener(eventListener2);
                }


                //


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        Log.i("testnn","444");

        rootRef.addListenerForSingleValueEvent(eventListener);








        requested.setOnClickListener(new View.OnClickListener() {
            /**
             * jump to BorrowerRequest.class
             * @param v
             */
            public void onClick(View v) {
                Intent intent = new Intent(BorrowerMenu.this, BorrowerRequest.class);
                startActivity(intent);
                //BorrowerMenu.this.finish();


            }
        });

        bookList.setOnClickListener(new View.OnClickListener() {
            /**
             * jump to BorrowBookList.class
             * @param v
             */
            public void onClick(View v) {
                Intent intent = new Intent(BorrowerMenu.this, BorrowBookList.class);
                startActivity(intent);
                //BorrowerMenu.this.finish();


            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            /**
             * jump to search.class
             * @param v
             */
            public void onClick(View v) {
                Intent intent = new Intent(BorrowerMenu.this, Search.class);
                intent.putExtra("flag","borrower");
                startActivity(intent);
                //BorrowerMenu.this.finish();


            }
        });

        scan.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(BorrowerMenu.this, check_to_scan.class);
                intent.putExtra("user","borrower");
                startActivityForResult(intent, 3);
                //BorrowerMenu.this.finish();
            }
        });

        acceptedButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(BorrowerMenu.this, ViewAcceptedRequests.class);
                startActivity(intent);

                //BorrowerMenu.this.finish();
            }
        });

        NA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BorrowerMenu.this, home_page.class);
                startActivity(intent);
            }
        });

        NA2.setOnClickListener(new View.OnClickListener() {


            //hello
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BorrowerMenu.this, OwnerHomeActivity.class);
                startActivity(intent);

            }
        });

        NA3.setOnClickListener(new View.OnClickListener() {


            //hello
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Already in borrower page",Toast.LENGTH_SHORT).show();
                //finish();

            }
        });

        NA4.setOnClickListener(new View.OnClickListener() {


            //hello
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BorrowerMenu.this, SearchingUserDetail.class);
                intent.putExtra("profileID",uid);
                intent.putExtra("flag", "owner");
                startActivity(intent);
                //finish();

            }
        });



    }




    @Override
    public void onBackPressed(){
        Intent intent = new Intent(BorrowerMenu.this,home_page.class);
        startActivity(intent);
    }

}