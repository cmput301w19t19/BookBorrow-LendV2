package com.example.y.bookborrow_lendv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class MyBookList extends AppCompatActivity {
    private ListView myBookList;
    private ArrayList<book> bookList = new ArrayList<>();
    private bookAdapter myBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_list);

        ImageButton addBook = (ImageButton) findViewById(R.id.addBook);
        Button available = (Button) findViewById(R.id.availableFilter);
        Button requested = (Button) findViewById(R.id.requestedFilter);
        Button accepted = (Button) findViewById(R.id.acceptedFilter);
        Button borrowed = (Button) findViewById(R.id.borrowedFilter);
        myBookList = (ListView) findViewById(R.id.BookList);

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyBookList.this,PrivateBookDetails.class);
                intent.putExtra("0","0");
                startActivityForResult(intent,0);
            }
        });

        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onStart(){
        // TODO Auto-generated method stub
        super.onStart();
        bookList = new ArrayList<>();
        myBookAdapter = new bookAdapter(this, bookList);
        myBookList.setAdapter(myBookAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode ,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==0&&resultCode==1){
            String bookID = data.getStringExtra("ID");
            //bookList.add(book1);
            //adapter.notifyDataSetChanged();
        }
    }
}
