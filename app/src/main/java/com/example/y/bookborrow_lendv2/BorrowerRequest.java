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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BorrowerRequest extends AppCompatActivity {

    private ArrayList<book> requestedBookList = new ArrayList<book>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_request);

        ListView acceptListView = (ListView)findViewById(R.id.acceptListView);
        ListView requestListView = (ListView)findViewById(R.id.requestListView);

        //how to pass a specify brrower object that is current login borrower
        borrower thisborrower = new borrower();
        requestedBookList = thisborrower.getRequestedBookList();

        ArrayAdapter<book> arrayAdapter = new ArrayAdapter<book>(this,android.R.layout.simple_list_item_1
        ,requestedBookList);

        requestListView.setAdapter(arrayAdapter);

        //com s....
        //ddd










        //showAcceptRequest();
        //showWatchList();
    }

    public void showAcceptRequest(){

    }

    public void showWatchList(){

    }
}
