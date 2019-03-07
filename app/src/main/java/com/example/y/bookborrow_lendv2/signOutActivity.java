package com.example.y.bookborrow_lendv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class signOutActivity extends AppCompatActivity {


    private Button signOutButton;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);
        signOutButton = (Button) findViewById(R.id.signOutButton);





        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                signOut();


            }
        });
    }


    //sign out method
    public void signOut() {
        auth = FirebaseAuth.getInstance();

        auth.signOut();
    }
}
