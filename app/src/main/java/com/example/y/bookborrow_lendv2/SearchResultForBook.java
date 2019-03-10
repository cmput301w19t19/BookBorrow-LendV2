package com.example.y.bookborrow_lendv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

import java.security.KeyStore;

public class SearchResultForBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_for_book);
    }

    public void newSearch(View view){
        //BookDatabase= FirebaseDatabase.getInstance().getReference("books");



    }

    public void backHome(View view){


    }

    public void displayBookName(View view){


    }


    public void displayBookDetail(View view){


    }

    public void displayBookImage(View view){

    }
}
