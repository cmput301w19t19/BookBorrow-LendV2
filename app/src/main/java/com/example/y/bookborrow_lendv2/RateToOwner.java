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
import android.widget.EditText;
import android.widget.TextView;

public class RateToOwner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_to_owner);

        TextView O_username = (TextView) findViewById(R.id.Owner_username);
        TextView O_description = (TextView) findViewById(R.id.Owner_description);

        EditText O_rate_borrower = (EditText) findViewById(R.id.edit_owner_rate);
        EditText O_com_borrower = (EditText) findViewById(R.id.edit_owner_comment);

        TextView B_username = (TextView) findViewById(R.id.Book_name);
        TextView B_description = (TextView) findViewById(R.id.Book_author);

        EditText B_rate_borrower = (EditText) findViewById(R.id.edit_book_rate);
        EditText B_com_borrower = (EditText) findViewById(R.id.edit_book_comment);

        set_O_UsernameAndDescription();
        set_O_RateAndComment();

        set_B_UsernameAndDescription();
        set_B_RateAndComment();
    }

    public void set_O_UsernameAndDescription(){

    }

    public void set_O_RateAndComment(){

    }

    public void set_B_UsernameAndDescription(){

    }

    public void set_B_RateAndComment(){

    }
}
