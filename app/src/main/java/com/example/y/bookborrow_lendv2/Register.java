package com.example.y.bookborrow_lendv2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.BitSet;

public class Register extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private EditText inputEmail, inputPassword, userName, phone;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        auth = FirebaseAuth.getInstance();

        inputEmail = (EditText) findViewById(R.id.InputRegisterEmail);
        inputPassword = (EditText) findViewById(R.id.InputRegisterPassword);
        userName = (EditText) findViewById(R.id.InputName_);
        phone = (EditText) findViewById(R.id.InputPhone_);
        button = (Button) findViewById(R.id.ButtonDone);





        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                 String email = inputEmail.getText().toString().trim();

                String password = inputPassword.getText().toString().trim();

                String username = userName.getText().toString().trim();

                String Phone = phone.getText().toString().trim();

                register(email,password,username,Phone);


            }});





    }

    public void register(final String email,final String password, final String username, final String phone){
        Log.w("aaaaaaaa","dddddd");


        mDatabase = FirebaseDatabase.getInstance().getReference();





        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address you want to register with!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        //create user
        auth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(Register.this, "createUserWithEmail:onComplete:"
                                + task.isSuccessful(), Toast.LENGTH_SHORT).show();



                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //Create a user and store it in firebase database
                            FirebaseUser user = auth.getCurrentUser();
                            String uid = user.getUid();

                            NormalUser newUser = new NormalUser();
                            borrower newBorrower = new borrower();
                            lender newLender = new lender();

                            mDatabase = FirebaseDatabase.getInstance().getReference();

                            newUser.setEmail(email);
                            newUser.setUid(uid);
                            newUser.setPassword(password);
                            newUser.setName(username);
                            newUser.setPhone(phone);


                            newBorrower.setEmail(email);
                            newBorrower.setUid(uid);
                            newBorrower.setName(username);
                            newBorrower.setPhone(phone);
                            newBorrower.setToFirebase(uid,email);


                            newLender.setEmail(email);
                            newLender.setUid(uid);
                            newLender.setName(username);
                            newLender.setPhone(phone);
                            newLender.setToFirebase(uid,email);


                            String key = uid;
                            mDatabase.child("users").child(key).setValue(newUser);
                            //mDatabase.child("borrowers").child(key).setValue(newBorrower);
                            //mDatabase.child("lenders").child(key).setValue(newLender);

                            Toast.makeText(Register.this, "Authentication success!" + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            SignIn(email,password);
                            //Intent intent = new Intent(loginAct.this, profile.class);
                            // startActivity(intent);
                        }
                    }});
    }


    public void SignIn(String email, String password){
        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Register.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getApplicationContext(), "login ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, home_page.class);
                        startActivity(intent);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        //progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();

                        } else {
                            FirebaseUser user = auth.getCurrentUser();
                            String uid = user.getUid();

                            // On login button click, storing our username into normalUser,lender borrower classes.
                            //Singleton Pattern implemented here
                            NormalUser.Instance().setUid(uid);
                            borrower.Instance().setUid(uid);
                            lender.Instance().setUid(uid);



                            Toast.makeText(getApplicationContext(), "login Success!", Toast.LENGTH_SHORT).show();


                            Intent i = new Intent(Register.this, home_page.class);
                            startActivity(i);


                        }
                    }


                });
    }


}
