/*
 * Class SearchResultBook.java
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

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.media.Image;

import java.util.ArrayList;


/**
 * This activity does the book search using keyword and shows the search results
 * @version 1.0
 */
public class SearchResultForBook extends AppCompatActivity {

    private ListView mResultList;
    private ListView userResultList;
    private ArrayList<book> books = new ArrayList<>();
    private ArrayList<NormalUser> users = new ArrayList<>();

    private SearchBookAdapter adapter;
    private SearchPersonAdapter adapter2;

    private DatabaseReference mBookDatabase;
    private DatabaseReference mUserDatabase;



    //private String flag;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    public String Keyword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_for_book);

        mBookDatabase = FirebaseDatabase.getInstance().getReference("book");
        //mSearchWord = (EditText) findViewById(R.id.editText);
        Button newSearch = (Button) findViewById(R.id.NewSearch1);
        Button backHome = (Button) findViewById(R.id.HomeButton1);

        mResultList = findViewById(R.id.ListBook);
        userResultList = findViewById(R.id.ListUser);

        Intent intent = getIntent();
        Keyword = intent.getStringExtra("key");




        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference booksRef = rootRef.child("book");
        //final ArrayList<book> bookLists = new ArrayList<>();
        Log.i("bbbbbb","hello1");

        // eventListener for searching book title's keyword

        ValueEventListener eventListener = new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean found1_;
                Boolean found2_;
                // String search = "Elements";
                String search = Keyword;
                //String search = "Trevor Hastie Robert Tibshirani Jerome Friedman";
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    book bookdFound = ds.getValue(book.class);
                    //get the name of book from database
                    String title = bookdFound.getName();
                    Log.i("BookName",title);
                    String author = bookdFound.getAuthor();
                    String stat = bookdFound.getStatus();
                    //check if title contains keyword
                    found1_ = title.contains(search);
                    found2_ = author.contains(search);
                    //check if the book contains keywords is still available for other borrower
                    // if the book is still available, then show the book on the list
                    if (found1_ && !stat.equals("accepted") && !stat.equals("borrowed") ) {
                        books.add(bookdFound);
                    } else if ( found2_ && !stat.equals("accepted") && !stat.equals("borrowed")) {
                        books.add(bookdFound);
                    }
                    adapter.notifyDataSetChanged();
                    for (final book bookItem: books){
                        String bookID = bookItem.getID();
                        StorageReference imageRef = storageRef.child("book/"+bookID+"/1.jpg");

                        //when showing the book on adapter, attach the image of book
                        final long ONE_MEGABYTE = 10 * 1024 * 1024;
                        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Log.i("Result","search book success");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                bookItem.setImage(bitmap);
                                adapter.notifyDataSetChanged();
                                //bookPhoto.setImageBitmap(bitmap);
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("Result","failed");
                            }
                        });
                    }
                    //check if author contains keyword
/*
                    found = author.contains(search);

                    if (found && !stat.equals("accepted") && !stat.equals("borrowed")) {
                        books.add(bookdFound);
                    }

*/
                    //adapter = new SearchBookAdapter(SearchResultForBook.this, books);
                    //mResultList.setAdapter(adapter);


                }
                String size = Integer.toString(books.size());
                Log.i("Result","bookList size:"+size);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        booksRef.addListenerForSingleValueEvent(eventListener);



        searchPeopleResult(Keyword);





        newSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchResultForBook.this, Search.class);
                startActivity(intent);
            }
        });

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchResultForBook.this,home_page.class);
                startActivity(intent);
            }
        });
        books = new ArrayList<>();
        adapter = new SearchBookAdapter(this, books);
        mResultList.setAdapter(adapter);


        //after found the book contains keyworks, then turn to the publicBookDetails to show the list of book
        mResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                book bookItem = books.get(position);
                String bookId = bookItem.getID();
                Intent intent = new Intent(SearchResultForBook.this, PublicBookDetails.class);
                intent.putExtra("Id",bookId);
                intent.putExtra("Keyword",Keyword);
                intent.putExtra("flag","searchbook");
                startActivity(intent);
            }
        });

        //Show the detail of searching person with keywords
        userResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NormalUser u = users.get(position);
                String uid = u.getUid();
                Log.i("testProcess", "666");
                Intent i = new Intent(SearchResultForBook.this,SearchingUserDetail.class);
                i.putExtra("profileID",uid);
                i.putExtra("flag", "borrower");
                Log.i("testFlag", "666");
                startActivity(i);
            }
        });



    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(SearchResultForBook.this,Search.class);
        startActivity(intent);
    }


  public void searchPeopleResult(final String Keyword){
      users = new ArrayList<>();
      adapter2 = new SearchPersonAdapter(this, users);
      userResultList.setAdapter(adapter2);

    ValueEventListener eventListener = new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Boolean found1;
            Boolean found2;
            mUserDatabase = FirebaseDatabase.getInstance().getReference("users");
            userResultList = (ListView) findViewById(R.id.peopleList);

            // String search = "Elements";
            //String search = Keyword;

            //String search = "Trevor Hastie Robert Tibshirani Jerome Friedman";
            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                final NormalUser SearchedUser = ds.getValue(NormalUser.class);
                String userName = SearchedUser.getName();
                String userEmail = SearchedUser.getEmail();
                if (userName != null && userEmail != null) {
                    found1 = userName.contains(Keyword);
                    found2 = userEmail.contains(Keyword);
                    if (found1 || found2) {
                        String id = SearchedUser.getUid();
                        StorageReference imageRef = storageRef.child("user/" + id + "/1.jpg");
                        Log.i("Result","test"+imageRef.toString());


                        //attach image with the result
                        final long ONE_MEGABYTE = 10 * 1024 * 1024;
                        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Log.i("Result", " search people success");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                SearchedUser.setPhoto(bitmap);

                                adapter2.notifyDataSetChanged();
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("Result", "failed");
                            }
                        });
                        users.add(SearchedUser);
                        adapter2.notifyDataSetChanged();



                    }
                }





                String size2 = Integer.toString(users.size());
                adapter2.notifyDataSetChanged();
                Log.i("Result","userList size:"+size2);

            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
      };
      DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

      DatabaseReference usersRef = rootRef.child("users");
      usersRef.addListenerForSingleValueEvent(eventListener);
  }



}
