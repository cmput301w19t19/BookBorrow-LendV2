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
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * The type Rate to owner.
 */
public class RateToOwner extends AppCompatActivity {

    private book b;
    private bookISBN ISBN;
    private lender owner;

    /**
     * The Owner rate edit text.
     */
    EditText ownerRateEditText;
    /**
     * The Owner comment edit text.
     */
    EditText ownerCommentEditText;
    /**
     * The Book rate edit text.
     */
    EditText bookRateEditText;
    /**
     * The Book comment edit text.
     */
    EditText bookCommentEditText;

    /**
     * The Owner user email text view.
     */
    TextView ownerUserEmailTextView;
    /**
     * The Owner user name text view.
     */
    TextView ownerUserNameTextView;
    /**
     * The Book name text view.
     */
    TextView bookNameTextView;
    /**
     * The Book author text view.
     */
    TextView bookAuthorTextView;
    /**
     * The Book isbn text view.
     */
    TextView bookISBNTextView;


    /**
     * The Auth.
     */
    FirebaseAuth auth;
    /**
     * The User.
     */
    FirebaseUser user;
    /**
     * The M.
     */
    FirebaseDatabase m;
    /**
     * The Uid.
     */
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_to_owner);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uid = user.getUid();
        m = FirebaseDatabase.getInstance();

        ownerRateEditText = (EditText) findViewById(R.id.edit_owner_rate);
        ownerCommentEditText = (EditText) findViewById(R.id.edit_owner_comment);
        bookRateEditText = (EditText) findViewById(R.id.edit_book_rate);
        bookCommentEditText = (EditText) findViewById(R.id.edit_book_comment);

        Button saveButton = (Button)findViewById(R.id.RateToOwnerSaveButton);

        ownerUserNameTextView = (TextView) findViewById(R.id.Owner_username);
        ownerUserEmailTextView = (TextView)findViewById(R.id.Owner_user_email);
        bookNameTextView = (TextView) findViewById(R.id.Book_name);
        bookAuthorTextView = (TextView) findViewById(R.id.Book_author);
        bookISBNTextView = (TextView) findViewById(R.id.Book_ISBN);


        Intent i = getIntent();
        String bid = i.getStringExtra("0");

        /**
         * set basic book information
         */
        DatabaseReference r = m.getReference("book/"+bid);
        ValueEventListener bookLister = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    b = dataSnapshot.getValue(book.class);
                    String bookName = b.getName();
                    if (bookName != null){
                        bookNameTextView.setText(bookName);
                    }

                    String bookISBN = b.getISBN();
                    if (ISBN != null) {
                        bookISBNTextView.setText(bookISBN);
                    }

                    String author = b.getAuthor();
                    if (author != null) {
                        bookAuthorTextView.setText(b.getAuthor());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Fail to get data from database",Toast.LENGTH_SHORT).show();
            }
        };
        r.addListenerForSingleValueEvent(bookLister);


        /**
         * set basic owner information
         */
        String ownerUid = b.getOwnerID();
        r = m.getReference("lenders/" + ownerUid);
        ValueEventListener lenderListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists() ){
                   owner = dataSnapshot.getValue(lender.class);

                   String userEmail = owner.getEmail();
                   if (userEmail != null){
                       ownerUserEmailTextView.setText(userEmail);
                   }

                   String userName = owner.getName();
                   if (userName != null){
                       ownerUserNameTextView.setText(userName);
                   }

               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Fail to get data from database",Toast.LENGTH_SHORT).show();
            }
        };








    }

}
