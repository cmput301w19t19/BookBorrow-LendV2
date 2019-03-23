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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * @author: Bowei Li
 * @version 1.0
 */
public class OwnerHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        TextView myBooks = findViewById(R.id.select_owner_menu_1);
        TextView mySearch = findViewById(R.id.select_owner_menu_2);
        TextView myScan = findViewById(R.id.select_owner_menu_3);
        Button backButton = findViewById(R.id.back_button);

        myBooks.setOnClickListener(new View.OnClickListener() {
            /**
             * jump to MybookList class after click
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomeActivity.this,MyBookList.class);
                startActivity(intent);
            }
        });

        mySearch.setOnClickListener(new View.OnClickListener() {
            /**
             * jump to Search class after click
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomeActivity.this, Search.class);
                startActivity(intent);
            }
        });

        myScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomeActivity.this, check_to_scan.class);
                intent.putExtra("user","owner");
                startActivityForResult(intent, 3);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * jump to homepage class after click
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomeActivity.this, home_page.class);
                startActivity(intent);
            }
        });
    }



}
