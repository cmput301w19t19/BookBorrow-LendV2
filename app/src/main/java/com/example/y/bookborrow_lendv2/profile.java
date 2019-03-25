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


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *the page that shows the user's profile and allow logged in usesr to edit profile
 * @author  Yuan
 * @see user
 * @since 1.0
 */

public class profile extends AppCompatActivity {
    private Button updateButton;
    private TextView inputEmail,uneditableUserName;
    private EditText inputUserName, inputPhone,inputMessage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference();
    DatabaseReference DbRef = database.getReference();
    DatabaseReference borrowerRef = database.getReference();
    DatabaseReference lenderRef = database.getReference();



    private FirebaseAuth auth;
    private NormalUser currentU = new NormalUser();
    private lender currentL = new lender();
    private borrower currentB = new borrower();


    /** Called when the activity is first created. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        //get the current logged in usesr ID
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();

        updateButton = (Button) findViewById(R.id.UpdateButton);
        inputEmail = (TextView) findViewById(R.id.InputEmail);
        inputUserName = (EditText) findViewById(R.id.InputName);
        inputPhone = (EditText) findViewById(R.id.InputPhone);
        uneditableUserName = (TextView) findViewById(R.id.UserName) ;

        //line 53-78: load data from firebase and update UI
        DbRef = database.getReference("users/"+uid);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                NormalUser currentUser = dataSnapshot.getValue(NormalUser.class);
                lender currentLender = dataSnapshot.getValue(lender.class);
                currentL = currentLender;
                borrower currentBorrower =dataSnapshot.getValue(borrower.class);
                currentB = currentBorrower;
                String email = currentUser.getEmail();
                currentU = currentUser;
                String UserName = currentUser.getName();
                String Phone = currentUser.getPhone();
                inputEmail.setText(email, TextView.BufferType.EDITABLE);
                inputUserName.setText(UserName, TextView.BufferType.EDITABLE);
                inputPhone.setText(Phone, TextView.BufferType.EDITABLE);
                uneditableUserName.setText(UserName);

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w( "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        DbRef.addValueEventListener(postListener);





        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String userName = inputUserName.getText().toString();
                String phone = inputPhone.getText().toString();
                auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                String uid = user.getUid();
                dbRef = database.getReference();
                borrowerRef = database.getReference();
                lenderRef = database.getReference();
                //dbRef.child("users").child(uid).child("email").setValue("Goodbye");
                dbRef = database.getReference();


                currentU.setName(userName);
                currentU.setPhone(phone);
                currentU.setEmail(email);
                currentU.setToFirebase();

                currentB.setName(userName);
                currentB.setNameToFireBase(uid,userName);
                //currentL.setName(userName);
                currentL.setNameToFireBase(uid,userName);



               // Map<String, Object> childUpdates = new HashMap<>();


                //dbRef.child("users").child(uid).setValue(currentU);
                //dbRef.child("lenders").child(uid).setValue(currentL);
                //dbRef.child("borrowers").child(uid).setValue(currentB);



                //childUpdates.put("users/"+uid+"/name", userName);
                //borrowerRef.updateChildren(childUpdates);

                //childUpdates.put("users/"+uid+"/name", userName);
                //lenderRef.updateChildren(childUpdates);




                //Update the static object as well
                Intent intent = new Intent(profile.this, home_page.class);
                startActivity(intent);


            }});


    }

}



