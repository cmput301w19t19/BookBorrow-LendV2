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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BorrowerMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_menu);

        TextView requested = (TextView) findViewById(R.id.select_borrow_menu_1);
        TextView bookList = (TextView) findViewById(R.id.select_borrow_menu_2);
        TextView search = (TextView) findViewById(R.id.select_borrow_menu_3);
        TextView scan = (TextView) findViewById(R.id.select_borrow_menu_4);


        requested.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //Intent intent = new Intent(BorrowerMenu.this, RequestMenu.class);
                //startActivity(intent);
                //BorrowerMenu.this.finish();


            }
        });

        bookList.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //Intent intent = new Intent(BorrowerMenu.this, MyBookList.class);
                //startActivity(intent);
                //BorrowerMenu.this.finish();


            }
        });

        search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //Intent intent = new Intent(BorrowerMenu.this, Search.class);
                //startActivity(intent);
                //BorrowerMenu.this.finish();


            }
        });

        scan.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Intent intent = new Intent(BorrowerMenu.this, Scan.class);
                // startActivity(intent);
                // BorrowerMenu.this.finish();

            }
        });
    }
}
