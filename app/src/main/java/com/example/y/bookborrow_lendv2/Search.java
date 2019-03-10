/*
 * Copyright 2019 TEAM01
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

/**
 * This is our search pages "can look for the result by person and books"
 */
public class Search extends AppCompatActivity {

    private EditText inputKeyword;
    private Button sPersonButton, sBookButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        inputKeyword = (EditText) findViewById(R.id.editText);
        sBookButton= (Button) findViewById(R.id.See_Result_of_BookButton);
        sPersonButton= (Button) findViewById(R.id.See_Result_of_PersonButton);

        sBookButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String Keyword = inputKeyword.getText().toString();

                if (TextUtils.isEmpty(Keyword)) {
                    Toast.makeText(getApplicationContext(), "Please enter a Keyword!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent= new Intent();
                intent.setClass(Search.this, SearchResultForBook.class);
                startActivity(intent);

            }
        });

        sPersonButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String Person = inputKeyword.getText().toString();

                if (TextUtils.isEmpty(Person)) {
                    Toast.makeText(getApplicationContext(), "Please enter a Keyword!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent= new Intent();
                intent.setClass(Search.this, SearchResultForPeople.class);
                startActivity(intent);


            }
        });
    }
}