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

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.ArrayList;


/**
 * This activity does the people search using keyword and shows the search results
 */
public class SearchResultForPeople extends AppCompatActivity {

    private ListView mResultList;
    private DatabaseReference mUserDatabase;
    private ArrayList<NormalUser> users = new ArrayList<>();
    private SearchPersonAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_for_people);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("users");
        //mSearchWord = findViewById(R.id.editText);
        Button newSearch = (Button) findViewById(R.id.NewSearch);
        Button backHome = (Button) findViewById(R.id.HomeButton);

        mResultList = (ListView) findViewById(R.id.peopleList);
        //mResultList.setHasFixedSize(true);
        //mResultList.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        final String Keyword = intent.getStringExtra("key");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("users");
        //final ArrayList<book> bookLists = new ArrayList<>();

        // eventListener for searching book title's keyword

        ValueEventListener eventListener = new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean found;
                // String search = "Elements";
                //String search = Keyword;

                //String search = "Trevor Hastie Robert Tibshirani Jerome Friedman";
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    NormalUser user = ds.getValue(NormalUser.class);
                    String userName = user.getName();
                    String email = user.getEmail();
                    //check if user's name contains keyword
                    found = userName.contains(Keyword);
                    if (found) {
                        users.add(user);
                    }




                    String size = Integer.toString(users.size());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        usersRef.addListenerForSingleValueEvent(eventListener);











        newSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchResultForPeople.this, Search.class);
                startActivity(intent);
            }
        });

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchResultForPeople.this, home_page.class);
                startActivity(intent);
            }
        });


        users = new ArrayList<>();
        adapter = new SearchPersonAdapter(this, users);
        mResultList.setAdapter(adapter);

    }





}
