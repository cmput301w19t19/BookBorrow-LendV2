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
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyBookList extends AppCompatActivity {
    private ListView myBookList;
    private ArrayList<book> bookList = new ArrayList<>();
    private bookAdapter myBookAdapter;
    private bookAdapter availableBookAdapter;
    private bookAdapter requestedBookAdapter;
    private bookAdapter borrowedBookAdapter;
    private bookAdapter acceptedBookAdapter;
    private ArrayList<book> availableBookList = new ArrayList<>();
    private ArrayList<book> requestedBookList = new ArrayList<>();
    private ArrayList<book> acceptedBookList = new ArrayList<>();
    private ArrayList<book> borrowedBookList = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_list);

        ImageButton addBook = (ImageButton) findViewById(R.id.addBook);
        final Button available = (Button) findViewById(R.id.availableFilter);
        Button requested = (Button) findViewById(R.id.requestedFilter);
        Button accepted = (Button) findViewById(R.id.acceptedFilter);
        Button borrowed = (Button) findViewById(R.id.borrowedFilter);
        Button all = (Button) findViewById(R.id.allButton);
        myBookList = (ListView) findViewById(R.id.BookList);

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyBookList.this,EditBookDetail.class);
                intent.putExtra("0","0");
                startActivityForResult(intent,0);
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
                        Toast.makeText(MyBookList.this,"hello", Toast.LENGTH_SHORT).show();
                    }
                }
                availableBookAdapter = new bookAdapter(MyBookList.this,availableBookList);
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
                requestedBookAdapter = new bookAdapter(MyBookList.this,requestedBookList);
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
                acceptedBookAdapter = new bookAdapter(MyBookList.this,acceptedBookList);
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
                borrowedBookAdapter = new bookAdapter(MyBookList.this,borrowedBookList);
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
                String bookId = bookItem.getID();
                Intent intent = new Intent(MyBookList.this,PrivateBookDetails.class);
                intent.putExtra("Id",bookId);
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
        String BookID = "0f45b9af-ebc7-4449-a6db-f88f9589a7c0";
        dbRef = database.getReference("book/"+BookID);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                book targetBook = dataSnapshot.getValue(book.class);
                   /* String bookName = targetBook.getName();
                    String currentBorrower = targetBook.getBorrowerName();
                    String description = targetBook.getDescriptionBundle();
                    String status = targetBook.getStatus();
                    String rating = targetBook.getBookRating().toString(); */
                bookList.add(targetBook);
                myBookAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w( "loadPost:onCancelled", databaseError.toException());
            }
        };
        dbRef.addValueEventListener(postListener);
    }



   //book book1 = new book(name, author, ISBN, longitude, latitude, description,title,rating,borrowerName,ownerName,status);
/*
    @Override
    protected void onStart(){
        // TODO Auto-generated method stub
        super.onStart();
        bookList = new ArrayList<>();
        myBookAdapter = new bookAdapter(this, bookList);
        myBookList.setAdapter(myBookAdapter);

    } */

    @Override
    protected void onActivityResult(int requestCode,int resultCode ,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==0&&resultCode==1){
            String bookID = data.getStringExtra("ID");
            String BookID = "0f45b9af-ebc7-4449-a6db-f88f9589a7c0";
            dbRef = database.getReference("book/"+BookID);
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    book targetBook = dataSnapshot.getValue(book.class);
                   /* String bookName = targetBook.getName();
                    String currentBorrower = targetBook.getBorrowerName();
                    String description = targetBook.getDescriptionBundle();
                    String status = targetBook.getStatus();
                    String rating = targetBook.getBookRating().toString(); */
                   bookList.add(targetBook);
                   myBookAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w( "loadPost:onCancelled", databaseError.toException());
                }
            };
            dbRef.addValueEventListener(postListener);
            //bookList.add(book1);
            //adapter.notifyDataSetChanged();
        }
    }
}
