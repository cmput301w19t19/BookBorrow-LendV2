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

public class loginAct extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText inputEmail, inputPassword;
    private Button  loginButton, registerButton;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {

            startActivity(new Intent(loginAct.this, signOutActivity.class));
            finish();
        }

        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.password_editText);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);


        registerButton.setOnClickListener(new View.OnClickListener() {


            //hello
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();

                ///
                //FirebaseDatabase

                    /*
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    String bookname = "The Elements of Statistical Learning";
                    book b = new book();
                    b.setAuthor("Trevor Hastie Robert Tibshirani Jerome Friedman");
                    b.setDescription("statistic machine learning");
                    b.setName(bookname);

                    b.setToFirebase();
                    b.setStatusToRequested();


                    //DatabaseReference ref = database.getReference("book");
                    //ref.child("book").child(b.getID()).setValue(b);



                    book b = new book();

/*

                    Toast.makeText(getApplicationContext(),"create a book",Toast.LENGTH_LONG);
                    Log.i("testnnn",b.getID());
                    Toast.makeText(getApplicationContext(),b.getID(),Toast.LENGTH_LONG);
                    Toast.makeText(getApplicationContext(),b.getName(),Toast.LENGTH_LONG);
                    Toast.makeText(getApplicationContext(),b.getAuthor(),Toast.LENGTH_LONG);



*/


                /// */


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
                                    newBorrower.setEmail(email);
                                    newBorrower.setUid(uid);
                                    newLender.setEmail(email);
                                    newBorrower.setUid(uid);


                                    newUser.setPassword(password);
                                    String key = uid;
                                    mDatabase.child("users").child(key).setValue(newUser);
                                    mDatabase.child("borrowers").child(key).setValue(newBorrower);
                                    mDatabase.child("lenders").child(key).setValue(newLender);

                                    Toast.makeText(loginAct.this, "Authentication success!" + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }});

                //startActivity(new Intent(loginAct.this, signOutActivity.class));
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = inputEmail.getText().toString();
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
                                    NormalUser.Instance().setUid(uid);
                                    borrower.Instance().setUid(uid);
                                    lender.Instance().setUid(uid);


                                    Toast.makeText(getApplicationContext(), "userID:"+lender.Instance().getUid(), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(loginAct.this, signOutActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        });




            }

        });
    }
}
