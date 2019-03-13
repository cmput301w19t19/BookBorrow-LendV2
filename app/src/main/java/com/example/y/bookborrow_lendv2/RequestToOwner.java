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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


import java.util.ArrayList;

/**
 * this class is a page allow borrower request book, books are displayed on listview
 *  @author
 *  @version 1.0
 *
 */
public class RequestToOwner extends AppCompatActivity {

    private ListView listview;
    private ArrayList<B_request> mDatas;
    private Request_Book_MyAdapter mAdapter;
    FirebaseDatabase m = FirebaseDatabase.getInstance();
    DatabaseReference dbHolder;
    DatabaseReference Holder;
    DatabaseReference dbBorrower;
    DatabaseReference dbBook;
    book b;
    private ArrayList<String> borrowerID = new ArrayList<String>();
    private String borrower_username;
    private Button accept, delete;

    //private List<HashMap<String, Object>> M_list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_to_owner);


        // get the book ID by intent

        Intent intent = getIntent();
        final String book_ID = intent.getStringExtra("request");
        //final String book_ID = "45d11887-6961-41c0-916b-92b78c68dead";
        // gain a databaseReference
        dbHolder = m.getReference("book").child(book_ID).child("requestList") ;
        Log.i("test22", book_ID);

        //borrowerID.add("3abc1a17-0fec-427a-a103-d00283826755");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        final String b_ID = ds.getKey();
                        Holder = m.getReference("borrowers/" + b_ID);

                        ValueEventListener postListener2 = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                if (dataSnapshot2.exists()) {
                                    Log.i("test22", "fdsuifsiudauisd");
                                    borrower bor = dataSnapshot2.getValue(borrower.class);
                                    String b_user = bor.getName();
                                    String userID = bor.getUid();
                                    //Float b_rating = bor.getBorrowerRating();
                                    // test case

                                    // set the username and rating to the adapter array
                                    B_request request = new B_request(b_user, 0.0, userID);
                                    mDatas.add(request);
                                    mAdapter.notifyDataSetChanged();
                                    //Log.i("Yuanproblem2", mDatas.get(0).getUserID());
                                }
                                // get the username and rating from the borrower ID
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError2) {
                                Log.w("load: OnCancelled", databaseError2.toException());
                            }
                        };
                        Holder.addValueEventListener(postListener2);
                    }
                }
            }

                //ArrayList<String> borrowerID = (ArrayList<String>)dataSnapshot.getValue();




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("load: OnCancelled", databaseError.toException());
            }
        };

        // take the request list of the book
        dbHolder.addValueEventListener(postListener);

        // initial view layout and data
        mDatas = new ArrayList<B_request>();
        initView();
        initData(mDatas);

        /*listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Toast.makeText(getApplicationContext(),"caaaaaaaaaaa",Toast.LENGTH_LONG);

                // TODO Auto-generated method stub
                borrower_username = mDatas.get(position).getUserName();
                Log.i("test_username",borrower_username);
            }
        });*/


        accept = (Button) findViewById(R.id.B_R_acceptButton);
        delete = (Button) findViewById(R.id.B_R_declineButton);


        dbBook = m.getReference() ;
        dbBorrower = m.getReference();

        // accept the chosen request
        accept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("Accept", mDatas.get(0).toString());
                for(int j = 0; j < mDatas.size();j++){
                    if(mDatas.get(j).isSelected() == true){
                        dbBook.child("book").child(book_ID).child("status").setValue("accepted");
                        dbBorrower.child("borrowers").child(mDatas.get(j).getUserID()).child("BorrowerRequest").child(book_ID).setValue("true");
                        for(int i = 0; i < mDatas.size();i++)
                        {
                            mDatas.get(i).selected = true;
                        }
                        deleteRequest(mDatas, dbBorrower, dbHolder, book_ID);
                        mAdapter.notifyDataSetChanged();
                        //Log.i("Sucess", mDatas.get(j).getUserID());
                    }
                }
            }
        });


        // delete the chosen request
        delete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.i("Delete", mDatas.get(0).getUserID());
                deleteRequest(mDatas, dbBorrower, dbHolder, book_ID);
                mAdapter.notifyDataSetChanged();
            }
        });



    }

    private void initView(){
        listview = (ListView) findViewById(R.id.requestList);
    }

    private void initData(ArrayList<B_request> mDatas){
        //put the data into the class
        mAdapter = new Request_Book_MyAdapter(this, mDatas);
        listview.setAdapter(mAdapter);
    }


    private void deleteRequest(ArrayList<B_request> mDatas, DatabaseReference dbBorrower, DatabaseReference dbHolder, String book_ID){
        for(int j = 0; j < mDatas.size();j++){
            if(mDatas.get(j).isSelected() == true){
                dbHolder.child(mDatas.get(j).getUserID()).removeValue();
                dbBorrower.child("borrowers").child(mDatas.get(j).getUserID()).child("requestList").child(book_ID).removeValue() ;
                mDatas.remove(j);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
