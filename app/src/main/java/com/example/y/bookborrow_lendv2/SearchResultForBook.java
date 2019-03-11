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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.media.Image;

import java.util.ArrayList;

public class SearchResultForBook extends AppCompatActivity {

    private ListView mResultList;
    private ArrayList<book> books = new ArrayList<>();
    private SearchBookAdapter adapter;
    private DatabaseReference mBookDatabase;

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
                Boolean found1;
                Boolean found2;
                // String search = "Elements";
                String search = Keyword;
                Log.i("bbbbbb","wode"+search);
                //String search = "Trevor Hastie Robert Tibshirani Jerome Friedman";
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.i("bbbbbb","hello3");
                    book bookdFound = ds.getValue(book.class);
                    Log.i("bbbbbb","hello4");
                    String title = bookdFound.getName();
                    Log.i("bbbbbb",title);
                    String author = bookdFound.getAuthor();
                    Log.i("bbbbbb",author);
                    String stat = bookdFound.getStatus();
                    Log.i("bbbbbb",stat);
                    //check if title contains keyword
                    found1 = title.contains(search);
                    found2 = author.contains(search);
                    Log.i("bbbbbb","hello8");
                    if (found1 && !stat.equals("accepted") && !stat.equals("borrowed") ) {
                        books.add(bookdFound);
                    } else if ( found2 && !stat.equals("accepted") && !stat.equals("borrowed")) {
                        books.add(bookdFound);
                    }


                    //check if author contains keyword
/*
                    found = author.contains(search);

                    if (found && !stat.equals("accepted") && !stat.equals("borrowed")) {
                        books.add(bookdFound);
                    }

*/

                    adapter.notifyDataSetChanged();

                }
                String size = Integer.toString(books.size());

                Log.i("bbbbbbbbb", size);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        booksRef.addListenerForSingleValueEvent(eventListener);

        Log.i("bbbbbb","hello2");






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

        mResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                book bookItem = books.get(position);
                String bookId = bookItem.getID();
                Intent intent = new Intent(SearchResultForBook.this, PublicBookDetails.class);
                intent.putExtra("Id",bookId);
                intent.putExtra("Keyword",Keyword);
                startActivity(intent);
            }
        });
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1&&resultCode==1){
            adapter.notifyDataSetChanged();
        }
    }
*/



}
