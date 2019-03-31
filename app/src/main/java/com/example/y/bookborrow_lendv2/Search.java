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
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This is our search pages that allow user to enter keayword
 */
public class Search extends AppCompatActivity {
    /**
     * The Database.
     */
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    /**
     * The Db ref.
     */
    DatabaseReference DbRef;
    private FirebaseAuth auth;
    private EditText inputKeyword;
    private Button sPersonButton, sBookButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        inputKeyword = (EditText) findViewById(R.id.keyword);
        sBookButton= (Button) findViewById(R.id.See_Result_of_BookButton);
        sPersonButton= (Button) findViewById(R.id.See_Result_of_PersonButton);




        sBookButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String Keyword = inputKeyword.getText().toString();

                if (TextUtils.isEmpty(Keyword)) {
                    Toast.makeText(getApplicationContext(), "Please enter a Keyword!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //DbRef = database.getReference("book");
                    //DbRef.addListenerForSingleValueEvent();
                    Intent intent= new Intent();
                    intent.setClass(Search.this, SearchResultForBook.class);
                    intent.putExtra("key",Keyword);
                    startActivity(intent);
                }



            }
        });

        sPersonButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String Person = inputKeyword.getText().toString();

                if (TextUtils.isEmpty(Person)) {
                    Toast.makeText(getApplicationContext(), "Please enter a Keyword!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent= new Intent();
                    intent.setClass(Search.this, SearchResultForPeople.class);
                    intent.putExtra("key",Person);
                    startActivity(intent);

                }



            }
        });
    }
    /*@Override
    public void onBackPressed(){
        Intent intent = new Intent(Search.this,)
    }
    */

}