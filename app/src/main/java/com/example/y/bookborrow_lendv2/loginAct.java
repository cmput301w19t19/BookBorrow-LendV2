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


/**
 *
 *this is the first activity which allow user to register and loin
 * @author  Yuan
 * @see user
 * @since 1.0
 */


public class loginAct extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText inputEmail, inputPassword;
    private Button  loginButton, registerButton;
    private DatabaseReference mDatabase;
    /** Called when the activity is first created. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {

            auth.signOut();
        }

        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.password_editText);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);

        /**
         * if register button cliscked, the user is abble to create a account with
         * valid password and email
         */
        registerButton.setOnClickListener(new View.OnClickListener() {


            //hello
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }


                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(loginAct.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(loginAct.this, "createUserWithEmail:onComplete:"
                                        + task.isSuccessful(), Toast.LENGTH_SHORT).show();



                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(loginAct.this, "Authentication failed." + task.getException(),
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

                                    newBorrower.setEmail(email);
                                    newBorrower.setUid(uid);
                                    newBorrower.setToFirebase(uid,email);


                                    newLender.setEmail(email);
                                    newLender.setUid(uid);
                                    newLender.setToFirebase(uid,email);




                                    newUser.setPassword(password);
                                    String key = uid;
                                    mDatabase.child("users").child(key).setValue(newUser);
                                    //mDatabase.child("borrowers").child(key).setValue(newBorrower);
                                    //mDatabase.child("lenders").child(key).setValue(newLender);

                                    Toast.makeText(loginAct.this, "Authentication success!" + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }});

            }
        });


        /**
         * when the login button is clicked, the registered user is able to login with matched
         * password and email
         */
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter user name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }


                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(loginAct.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getApplicationContext(), "login ", Toast.LENGTH_SHORT).show();

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
                                    //lender.Instance().setUid(uid);



                                    Toast.makeText(getApplicationContext(), "login Success!", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(loginAct.this, home_page.class);
                                    //Intent intent = new Intent(loginAct.this, RateToBorrower.class);
                                    startActivity(intent);


                                }
                            }

                        });




            }

        });

    }

    /**
     * this method is defined for intent test
     * @return loggedin user id
     */
    public String returnCurrentUser(){
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();
        return uid;
    }
}
