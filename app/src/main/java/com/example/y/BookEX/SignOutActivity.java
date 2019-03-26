package com.example.y.BookEX;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * The type Sign out activity.
 */
public class SignOutActivity extends AppCompatActivity {


    private Button signOutButton;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);
        signOutButton = (Button) findViewById(R.id.signOutButton);




        //Toast.makeText(getApplicationContext(), "currentUser logout userID:"+ Lender.Instance().getUid(), Toast.LENGTH_SHORT).show();

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signOut();
               // startActivity(new Intent( SignOutActivity.this,LoginAct.class));


            }
        });
    }


    /**
     * Sign out.
     */
//sign out method
    public void signOut() {
        auth = FirebaseAuth.getInstance();

        auth.signOut();
        startActivity(new Intent( SignOutActivity.this, LoginAct.class));


    }
}
