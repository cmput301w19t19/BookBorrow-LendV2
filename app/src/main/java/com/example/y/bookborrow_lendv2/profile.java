package com.example.y.bookborrow_lendv2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity {
    private Button updateButton;
    private TextView inputEmail;
    private EditText inputUserName, inputPhone,inputMessage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference();
    DatabaseReference DbRef = database.getReference();

    private FirebaseAuth auth;


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
        //inputMessage = (EditText) findViewById(R.id.InputMessage);

        //line 53-78: load data from firebase and update UI
        DbRef = database.getReference("users/"+uid);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                NormalUser currentUser = dataSnapshot.getValue(NormalUser.class);
                String email = currentUser.getEmail();

                String UserName = currentUser.getName();
                String Phone = currentUser.getPhone();
                inputEmail.setText(email, TextView.BufferType.EDITABLE);
                inputUserName.setText(UserName, TextView.BufferType.EDITABLE);
                inputPhone.setText(Phone, TextView.BufferType.EDITABLE);

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
                //dbRef.child("users").child(uid).child("email").setValue("Goodbye");
                dbRef = database.getReference("users");

                Map<String, Object> childUpdates = new HashMap<>();

                childUpdates.put(uid+"/email", email);
                childUpdates.put(uid+"/name", userName);
                childUpdates.put(uid+"/phone", phone);

                dbRef.updateChildren(childUpdates);

                //Update the static object as well



            }});


    }

}



