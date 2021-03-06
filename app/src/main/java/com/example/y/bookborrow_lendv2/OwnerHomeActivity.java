/*
 * Class OwnerHomeActivity.java
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

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import org.w3c.dom.Text;

import java.security.acl.Owner;
import java.util.ArrayList;

/**
 * The type Owner home activity.
 * This activity show the menu of things that user can do when user want to share his book
 * Legal things that a owner can do include:
 * 1> See my book list which is ready to share;
 * 2> Search for books that are in available and requested status;
 * 3> and scan a ISBN on a book to check self-book detail or ready to lend a book
 * 4> There is a bottom navigation bar help user to jump to HomePage, Borrower Menu and Profile page
 *
 * @version 1.0
 * @author: Bowei Li
 * @see MyBookList
 * @see Search
 * @see check_to_scan
 * @see home_page
 * @see BorrowerMenu
 * @see profile
 *
 */
public class OwnerHomeActivity extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference DbRef = database.getReference();
    private FirebaseAuth auth;
    private book targetBook;
    private ArrayList<String> BookList = new ArrayList<>();
    private String newRequestListSize;

    private ImageButton NA1;
    private ImageButton NA2;
    private ImageButton NA3;
    private ImageButton NA4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        TextView myBooks = findViewById(R.id.select_owner_menu_1);
        TextView mySearch = findViewById(R.id.select_owner_menu_2);
        TextView myScan = findViewById(R.id.select_owner_menu_3);

        NA1 = (ImageButton)findViewById(R.id.NA31);
        NA2 = (ImageButton)findViewById(R.id.NA32);
        NA3 = (ImageButton)findViewById(R.id.NA33);
        NA4 = (ImageButton)findViewById(R.id.NA34);

        ImageButton button = findViewById(R.id.Ibutton2);
        final TextView newRequestMessage = findViewById(R.id.newRequest);

        newRequestMessage.setVisibility(View.INVISIBLE);



        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        final String uid = user.getUid();

        DatabaseReference rootRef = database.getReference("lenders").child(uid).child("ListOfNewRequests");

        //set badge view
        final BadgeView badge = new BadgeView(this,button );


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    final String bookID = ds.getKey();


                    DatabaseReference dbRef = database.getReference("lenders").child(uid).child("ListOfNewRequests").child(bookID);
                    ValueEventListener eventListener2 = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                                if (ds1.getKey().equals("checkedByOwner")) {
                                    if (ds1.getValue().equals(false)) {
                                        BookList.add(bookID);
                                        newRequestListSize = Integer.toString(BookList.size());




                                    }
                                }
                            }
                            for (String BookID : BookList){
                                DatabaseReference Ref = database.getReference("book").
                                        child(BookID);


                                ValueEventListener eventListener3 = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                                            if (ds1.getKey().equals("status")) {
                                                if (ds1.getValue().equals("accepted")) {
                                                    BookList.remove(bookID);


                                                }
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }};
                                Ref.addValueEventListener(eventListener3);



                            }






                            if  (BookList.size() > 0){
                                badge.setText(Integer.toString(BookList.size()));

                                badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);

                                newRequestMessage.setVisibility(View.VISIBLE);

                                badge.show();
                            }


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

        rootRef.addListenerForSingleValueEvent(eventListener);




        button.setOnClickListener(new View.OnClickListener() {
            /**
             * jump to MybookList class after click
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomeActivity.this,ViewRequests.class);
                startActivity(intent);
            }
        });


        myBooks.setOnClickListener(new View.OnClickListener() {
            /**
             * jump to MybookList class after click
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomeActivity.this,MyBookList.class);
                startActivity(intent);
            }
        });

        mySearch.setOnClickListener(new View.OnClickListener() {
            /**
             * jump to Search class after click
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomeActivity.this, Search.class);
                intent.putExtra("flag","owner");
                startActivity(intent);
            }
        });

        myScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomeActivity.this, check_to_scan.class);
                intent.putExtra("user","owner");
                startActivityForResult(intent, 3);
            }
        });

        NA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomeActivity.this, home_page.class);
                startActivity(intent);
                finish();
            }
        });

        NA2.setOnClickListener(new View.OnClickListener() {


            //hello
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Already in owner page",Toast.LENGTH_SHORT).show();

            }
        });

        NA3.setOnClickListener(new View.OnClickListener() {


            //hello
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomeActivity.this, BorrowerMenu.class);
                startActivity(intent);
                finish();

            }
        });

        NA4.setOnClickListener(new View.OnClickListener() {


            //hello
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomeActivity.this, SearchingUserDetail.class);
                intent.putExtra("profileID",uid);
                intent.putExtra("flag", "owner");
                startActivity(intent);
                finish();

            }
        });


    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(OwnerHomeActivity.this,home_page.class);
        startActivity(intent);
    }


}