package com.example.y.bookborrow_lendv2;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference DbRef = database.getReference();
    private FirebaseAuth auth;
    private book targetBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_list);

        Log.i("testnn","111");



        ImageButton addBook = findViewById(R.id.addBook);
        final Button available = (Button) findViewById(R.id.availableFilter);
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
                    String bookID = ds.getKey();
                    DbRef = database.getReference("book/"+bookID);
                    ValueEventListener eventListener1 = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            Log.i("test22","hello");
                            targetBook = dataSnapshot1.getValue(book.class);
                            Log.i("test22",targetBook.getName());
                            bookList.add(targetBook);

                            myBookAdapter.notifyDataSetChanged();
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
                Log.i("testsize3",Integer.toString(booksID.size()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        Log.i("testnn","444");

        rootRef.addListenerForSingleValueEvent(eventListener);
        /*Log.i("testsize1",Integer.toString(booksID.size()));
        booksID.add("hello");
        Log.i("testsize2",Integer.toString(booksID.size()));
        for(String bookID:booksID){
            DbRef = database.getReference("book/"+bookID);
            Log.i("test11",bookID);
            Toast.makeText(MyBookList.this,"test11",Toast.LENGTH_SHORT).show();
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    book targetBook = dataSnapshot.getValue(book.class);
                    bookList.add(targetBook);
                    Log.i("test22",targetBook.getID());
                    myBookAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w( "loadPost:onCancelled", databaseError.toException());
                }
            };
            DbRef.addValueEventListener(postListener);
        }


*/
        Log.i("testnn","555");
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyBookList.this, EditBookDetail.class);
                intent.putExtra("0", "0");
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

            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBookAdapter = new bookAdapter(MyBookList.this, bookList);
                myBookList.setAdapter(myBookAdapter);
            }
        });


        myBookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Implement this method
                book bookItem = bookList.get(position);
                Toast.makeText(MyBookList.this,bookItem.getAuthor(),Toast.LENGTH_SHORT).show();
                String bookId = bookItem.getID();
                Intent intent = new Intent(MyBookList.this, PrivateBookDetails.class);
                intent.putExtra("Id", bookId);
                startActivity(intent);
            }
        });


        /*String name = "111";
        String author = "Bowei";
        String ISBN = "1110";
        String description = "111 Bowei 1110";
        Double longitude = 2.0;
        Double latitude = 3.0;
        String title = "111";
        Double rating = 1.0;
        String borrowerName = "FY";
        String ownerName = "TY";
        String status = "available";

        book book1 = new book(name, author, ISBN, longitude, latitude, description,title,rating,borrowerName,ownerName,status);
        bookList = new ArrayList<>();
        bookList.add(book1);
        myBookAdapter = new bookAdapter(this, bookList);
        myBookList.setAdapter(myBookAdapter);
        */

        bookList = new ArrayList<>();
        myBookAdapter = new bookAdapter(this, bookList);
        myBookList.setAdapter(myBookAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode ,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==0&&resultCode==1){
            String bookID = data.getStringExtra("ID");
            //String BookID = "0f45b9af-ebc7-4449-a6db-f88f9589a7c0";
            DbRef = database.getReference("book/"+bookID);
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    book targetBook = dataSnapshot.getValue(book.class);
                    bookList.add(targetBook);
                    myBookAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w( "loadPost:onCancelled", databaseError.toException());
                }
            };
            DbRef.addValueEventListener(postListener);
            //bookList.add(book1);
            //adapter.notifyDataSetChanged();
        }

    }

}