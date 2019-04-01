/*
 * Class check_to_scan.java
 *
 * Version 2.0
 *
 * Date 2019.4.1
 *
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
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.util.ArrayList;

/**
 * This function is working for scanning
 * By scanning, user could check book details, borrow a book
 * and return a book
 *
 * @author Yuan Feng
 * @author bingqiWang
 * @since 1.0
 */
public class check_to_scan extends AppCompatActivity{

    private RadioGroup group;
    private String B_status;
    private Button scanning;

    FirebaseDatabase m = FirebaseDatabase.getInstance();
    DatabaseReference dbHolder;
    DatabaseReference DbRef;
    String user;
    private FirebaseAuth auth;

    String code;
    String selectedID = null;
    String bookStatus;
    String BookfirstScanned;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_to_scan);

        FirebaseAuth Auth = FirebaseAuth.getInstance();
        FirebaseUser users = Auth.getCurrentUser();
        uid = users.getUid();
        B_status = "borrow";

        Intent intent = getIntent();
        // need the message to know if it is borrower to scan or is owner to scan
        user = intent.getStringExtra("user");
        //user = "owner";



        group= this.findViewById(R.id.RadioGroup);
        TextView available = (TextView)findViewById(R.id.availableText);

        if(user.equals("borrower")){
            RadioButton radioButton = (RadioButton)findViewById(R.id.checkBookDetail);
            radioButton.setClickable(false);
            available.setText("not available");
        }
        else if(user.equals("owner")){
            RadioButton radioButton = (RadioButton)findViewById(R.id.checkBookDetail);
            radioButton.setClickable(true);
            available.setText("");
        }
        //expected error

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //get the id after click
                int radioButtonId = group.getCheckedRadioButtonId();
                //get the radioButton with that id
                RadioButton rb = (RadioButton)findViewById(radioButtonId);
                //get the text of the button
                String B_name = String.valueOf(rb.getText());
                switch (B_name){
                    case "Check book detail":
                        B_status = "detail";
                        break;
                    case "Borrow book":
                        B_status = "borrow";
                        break;
                    case "Return a book":
                        B_status = "return";
                        break;
                }
            }
        });


        scanning = (Button)findViewById(R.id.scan_button);
        scanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(check_to_scan.this)
                        .setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)//any type of ISBN
                        .setOrientationLocked(true)
                        .setPrompt("Please Point to QR code")
                        .setCameraId(0) //chose camera
                        .initiateScan();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult= IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult!=null){
            String result = intentResult.getContents();
            if (result!=null) {
                Toast.makeText(check_to_scan.this, "Checking QR code finished", Toast.LENGTH_LONG).show();
                //String bookID;
                selectedID = null;
                // code is the ISBN that the result of scanner
                code = result;
                auth = FirebaseAuth.getInstance();
                FirebaseUser users = auth.getCurrentUser();
                uid = users.getUid();
                if (user.equals("owner")) {
                    dbHolder = m.getReference("lenders").child(uid).child("MyBookList");
                }
                else if(user.equals("borrower") && B_status.equals("borrow")){
                    dbHolder = m.getReference("borrowers").child(uid).child("AcceptedList");
                    Log.i("bookI","hello2222");
                }
                else if(user.equals("borrower") && B_status.equals("return")){
                    dbHolder = m.getReference("borrowers").child(uid).child("BorrowBookList");
                }
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            final String bookID = ds.getKey();
                            DbRef = m.getReference("book/" + bookID);
                            Log.i("bookId","hello"+bookID);
                            ValueEventListener eventListener1 = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                    book targetBook = dataSnapshot1.getValue(book.class);
                                    String sampleISBN = targetBook.getISBN();

                                    if (code.equals(sampleISBN)) {
                                        selectedID = bookID;
                                    }
                                    if(selectedID != null) {
                                        DbRef = m.getReference("book/" + selectedID);


                                        ValueEventListener eventListener2 = new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                                //for (DataSnapshot ds : dataSnapshot2.getChildren()) {
                                                book FoundBook = dataSnapshot2.getValue(book.class);

                                                bookStatus = FoundBook.getStatus();
                                                BookfirstScanned = FoundBook.getFirstScanned();
                                                String BorrowerID = FoundBook.getBorrowerID();

                                                Log.i("B_status", B_status);

                                                if (B_status.equals("detail")) {
                                                    Intent intent = new Intent(check_to_scan.this, PrivateBookDetails.class);
                                                    // put the result code to private detail
                                                    intent.putExtra("Id", selectedID);
                                                    intent.putExtra("flag","scan");
                                                    startActivity(intent);
                                                } else if (B_status.equals("borrow") && user.equals("borrower")) {
                                                    if (bookStatus.equals("accepted") && BookfirstScanned.equals("true")) {
                                                        //first delete the book in accept List

                                                        m.getReference("borrowers").child(uid).child("AcceptedList").child(selectedID).removeValue();

                                                        //add book into BorrowBookList
                                                        m.getReference("borrowers").child(uid).child("BorrowBookList").child(selectedID).setValue(true);
                                                        DbRef = m.getReference("book");
                                                        DbRef.child(selectedID).child("status").setValue("borrowed");
                                                        DbRef.child(selectedID).child("borrowerID").setValue(uid);
                                                        DbRef.child(selectedID).child("firstScanned").setValue("false");
                                                    } else {
                                                        Toast.makeText(check_to_scan.this, "The Book is not ready to scan yet", Toast.LENGTH_LONG).show();
                                                        Log.i("Acceptborrower","6766");
                                                    }
                                                } else if (B_status.equals("borrow") && user.equals("owner")) {
                                                    if (bookStatus.equals("accepted") && BookfirstScanned.equals("false")) {
                                                        DbRef = m.getReference("book");
                                                        // set the checkmate to true
                                                        DbRef.child(selectedID).child("firstScanned").setValue("true");
                                                    } else {
                                                        Toast.makeText(check_to_scan.this, "The Book is not ready to scan yet", Toast.LENGTH_LONG).show();
                                                        Log.i("AcceptOwner","6766");
                                                    }
                                                } else if (B_status.equals("return") && user.equals("borrower")) {
                                                    if (bookStatus.equals("borrowed") && BookfirstScanned.equals("false")) {
                                                        DbRef = m.getReference("book");
                                                        // set the checkmate to false
                                                        DbRef.child(selectedID).child("firstScanned").setValue("true");

                                                        Log.i("test checkToScan", "rateToOwnerStart");

                                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                        Intent i = new Intent(getApplicationContext(), RateToOwner.class);
                                                        i.putExtra("bookID", selectedID);
                                                        startActivity(i);

                                                        Log.i("test checkToScan", "RateToOwnerFinish");
                                                        finish();
                                                        return;
                                                    } else {
                                                        Toast.makeText(check_to_scan.this, "The Book is not ready to scan yet", Toast.LENGTH_LONG).show();
                                                        Log.i("ReturnBorrower","6766");
                                                    }
                                                } else if (B_status.equals("return") && user.equals("owner")) {
                                                    if (bookStatus.equals("borrowed") && BookfirstScanned.equals("true")) {
                                                        //first delete the book in borrow List
                                                        m.getReference("borrowers").child(BorrowerID).child("BorrowBookList").child(selectedID).removeValue();

                                                        DbRef = m.getReference("book");
                                                        // change the status to available
                                                        DbRef.child(selectedID).child("status").setValue("available");

                                                        DbRef.child(selectedID).child("firstScanned").setValue("false");

                                                        Log.i("test checkToScan", "rateToBorrowerStart");

                                                        DatabaseReference r = FirebaseDatabase.getInstance().getReference("book/" + selectedID);
                                                        r.child("borrowerID").removeValue();

                                                        Intent i = new Intent(getApplicationContext(), RateToBorrower.class);
                                                        i.putExtra("borrowerID", BorrowerID);
                                                        i.putExtra("bookID", selectedID);
                                                        startActivity(i);

                                                        Log.i("test checkToScan", "rateToBorrowerFinish");
                                                        finish();
                                                        return;
                                                    } else {
                                                        Toast.makeText(check_to_scan.this, "The Book is not ready to scan yet", Toast.LENGTH_LONG).show();
                                                        Log.i("ReturnOwner","6766");
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError2) {
                                                Log.w("loadPost:onCancelled", databaseError2.toException());
                                            }
                                        };
                                        DbRef.addListenerForSingleValueEvent(eventListener2);

                                    }
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError1) {

                                }
                            };
                            DbRef.addListenerForSingleValueEvent(eventListener1);
                        }

                    }
                    @Override
                    public void onCancelled (DatabaseError databaseError){

                    }
                };
                dbHolder.addListenerForSingleValueEvent(eventListener);
            }
        }
    }
}