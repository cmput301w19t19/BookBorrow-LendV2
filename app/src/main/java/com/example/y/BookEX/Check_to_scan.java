package com.example.y.BookEX;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * This function is working for scanning
 * By scanning, User could check Book details, borrow a Book
 * and return a Book
 *
 * @author Yuan Feng
 * @since 1.0
 */
public class Check_to_scan extends AppCompatActivity{

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
        B_status = "borrow";

        FirebaseAuth Auth = FirebaseAuth.getInstance();
        FirebaseUser users = Auth.getCurrentUser();
        uid = users.getUid();
        Log.i("start the app", "fuck this bullshit");

        Intent intent = getIntent();
        // need the message to know if it is Borrower to scan or is owner to scan
        user = intent.getStringExtra("User");
        //User = "owner";



        group= this.findViewById(R.id.RadioGroup);
        TextView available = (TextView)findViewById(R.id.availableText);

        if(user.equals("Borrower")){
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
                    case "Check Book detail":
                        B_status = "detail";
                        break;
                    case "Borrow Book":
                        B_status = "borrow";
                        break;
                    case "Return a Book":
                        B_status = "return";
                        break;
                }
            }
        });


        scanning = (Button)findViewById(R.id.scan_button);
        scanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(Check_to_scan.this)
                        .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)//any type of ISBN
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
                Toast.makeText(Check_to_scan.this, "Checking QR code finished", Toast.LENGTH_LONG).show();
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
                else if(user.equals("Borrower") && B_status.equals("borrow")){
                    dbHolder = m.getReference("borrowers").child(uid).child("AcceptedList");
                }
                else if(user.equals("Borrower") && B_status.equals("return")){
                    dbHolder = m.getReference("borrowers").child(uid).child("BorrowBookList");
                }
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            final String bookID = ds.getKey();
                            DbRef = m.getReference("Book/" + bookID);

                            ValueEventListener eventListener1 = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                    Book targetBook = dataSnapshot1.getValue(Book.class);
                                    String sampleISBN = targetBook.getISBN();

                                    if (code.equals(sampleISBN)) {
                                        selectedID = bookID;
                                    }
                                    if(selectedID != null) {
                                        DbRef = m.getReference("Book/" + selectedID);

                                        ValueEventListener eventListener2 = new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                                //for (DataSnapshot ds : dataSnapshot2.getChildren()) {
                                                Book foundBook = dataSnapshot2.getValue(Book.class);

                                                bookStatus = foundBook.getStatus();
                                                BookfirstScanned = foundBook.getFirstScanned();

                                                Log.i("B_status", B_status);

                                                if (B_status.equals("detail")) {
                                                    Intent intent = new Intent(Check_to_scan.this, PrivateBookDetails.class);
                                                    // put the result code to private detail
                                                    intent.putExtra("Id", selectedID);
                                                    startActivityForResult(intent, 3);
                                                } else if (B_status.equals("borrow") && user.equals("Borrower")) {
                                                    if (bookStatus.equals("accepted") && BookfirstScanned.equals("true")) {
                                                        //first delete the Book in accept List

                                                        m.getReference("borrowers").child(uid).child("AcceptedList").child(selectedID).removeValue();

                                                        //add Book into BorrowBookList
                                                        m.getReference("borrowers").child(uid).child("BorrowBookList").child(selectedID).setValue(true);
                                                        DbRef = m.getReference("Book");
                                                        DbRef.child(selectedID).child("status").setValue("borrowed");
                                                        DbRef.child(selectedID).child("borrowerID").setValue(uid);
                                                        DbRef.child(selectedID).child("firstScanned").setValue("false");
                                                    } else {
                                                        Toast.makeText(Check_to_scan.this, "The Book is not ready to scan yet", Toast.LENGTH_LONG).show();

                                                    }
                                                } else if (B_status.equals("borrow") && user.equals("owner")) {
                                                    if (bookStatus.equals("accepted") && BookfirstScanned.equals("false")) {
                                                        DbRef = m.getReference("Book");
                                                        // set the checkmate to true
                                                        DbRef.child(selectedID).child("firstScanned").setValue("true");
                                                    } else {
                                                        Toast.makeText(Check_to_scan.this, "The Book is not ready to scan yet", Toast.LENGTH_LONG).show();
                                                    }
                                                } else if (B_status.equals("return") && user.equals("Borrower")) {
                                                    if (bookStatus.equals("borrowed") && BookfirstScanned.equals("false")) {
                                                        DbRef = m.getReference("Book");
                                                        // set the checkmate to false
                                                        DbRef.child(selectedID).child("firstScanned").setValue("true");
                                                    } else {
                                                        Toast.makeText(Check_to_scan.this, "The Book is not ready to scan yet", Toast.LENGTH_LONG).show();
                                                    }
                                                } else if (B_status.equals("return") && user.equals("Borrower")) {
                                                    if (bookStatus.equals("borrowed") && BookfirstScanned.equals("false")) {
                                                        DbRef = m.getReference("Book");
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
                                                        Toast.makeText(Check_to_scan.this, "The Book is not ready to scan yet", Toast.LENGTH_LONG).show();
                                                    }
                                                } else if (B_status.equals("return") && user.equals("owner")) {
                                                    if (bookStatus.equals("borrowed") && BookfirstScanned.equals("true")) {
                                                        //first delete the Book in borrow List
                                                        m.getReference("borrowers").child(uid).child("BorrowBookList").child(selectedID).removeValue();

                                                        DbRef = m.getReference("Book");
                                                        // change the status to available
                                                        DbRef.child(selectedID).child("status").setValue("available");

                                                        DbRef.child(selectedID).child("firstScanned").setValue("false");

                                                        Log.i("test checkToScan", "rateToBorrowerStart");

                                                        DatabaseReference r = FirebaseDatabase.getInstance().getReference("Book/" + selectedID);
                                                        String BorrowerID = foundBook.getBorrowerID();
                                                        r.child("borrowerID").removeValue();

                                                        Intent i = new Intent(getApplicationContext(), RateToBorrower.class);
                                                        i.putExtra("borrowerID", BorrowerID);
                                                        i.putExtra("bookID", selectedID);
                                                        startActivity(i);

                                                        Log.i("test checkToScan", "rateToBorrowerFinish");
                                                        finish();
                                                        return;
                                                    } else {
                                                        Toast.makeText(Check_to_scan.this, "The Book is not ready to scan yet", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }


                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError2) {
                                                Log.w("loadPost:onCancelled", databaseError2.toException());
                                            }
                                        };
                                        DbRef.addValueEventListener(eventListener2);

                                    }
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError1) {

                                }
                            };
                            DbRef.addValueEventListener(eventListener1);
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