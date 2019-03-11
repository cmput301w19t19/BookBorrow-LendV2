package com.example.y.bookborrow_lendv2;

/**
 * This activity handles home_page
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home_page extends AppCompatActivity {
    private FirebaseAuth auth;
    private TextView username;
    private DatabaseReference mDatabase;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference DbRef = database.getReference();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();

        Toast.makeText(getApplicationContext(), "Enter email address!"+uid, Toast.LENGTH_SHORT).show();


        //get current logged in user name

        DbRef = database.getReference("users/"+uid);
        username = (TextView) findViewById(R.id.UserName);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                username = (TextView) findViewById(R.id.UserName);

                // Get Post object and use the values to update the UI
                NormalUser currentUser = dataSnapshot.getValue(NormalUser.class);


                String UserName = currentUser.getName();

                username.setText(UserName);


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




        // First get the LinearLayout object.
        LinearLayout ownerLayout = (LinearLayout)findViewById(R.id.OwnerLayout);
        LinearLayout borrowerLayout = (LinearLayout)findViewById(R.id.BorrowerLayout);
        LinearLayout ProfileLayout = (LinearLayout)findViewById(R.id.ProfileLayout);
        LinearLayout logOut = (LinearLayout)findViewById(R.id.SignOutlayout);



      //need to modify the intent activities after other activites are done


        ownerLayout.setOnClickListener(new View.OnClickListener() {


            //hello
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, OwnerHomeActivity.class);
                startActivity(intent);
                //finish();

            }
        });


        borrowerLayout.setOnClickListener(new View.OnClickListener() {


            //hello
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, BorrowerMenu.class);
                startActivity(intent);
                //finish();

            }
        });


        ProfileLayout.setOnClickListener(new View.OnClickListener() {


            //hello
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, profile.class);
                startActivity(intent);
                //finish();

            }
        });


        logOut.setOnClickListener(new View.OnClickListener() {


            //hello
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, loginAct.class);
                startActivity(intent);
                //finish();

            }
        });





    }


}
