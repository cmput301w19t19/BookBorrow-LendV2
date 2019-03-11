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

public class LoginAct extends AppCompatActivity {
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

            auth.signOut();
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


                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    String bookname = "The Elements of Statistical Learning";
                    Book b = new Book();
                    b.setAuthor("Trevor Hastie Robert Tibshirani Jerome Friedman");
                    b.setDescription("statistic machine learning");
                    b.setName(bookname);

                    b.setToFirebase();
                    b.setStatusToRequested();


                    //DatabaseReference ref = database.getReference("Book");
                    //ref.child("Book").child(b.getID()).setValue(b);



                    //Book b = new Book();



                    Toast.makeText(getApplicationContext(),"create a Book",Toast.LENGTH_LONG);
                    Log.i("testnnn",b.getID());
                    Toast.makeText(getApplicationContext(),b.getID(),Toast.LENGTH_LONG);
                    Toast.makeText(getApplicationContext(),b.getName(),Toast.LENGTH_LONG);
                    Toast.makeText(getApplicationContext(),b.getAuthor(),Toast.LENGTH_LONG);






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


                //create User
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginAct.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(LoginAct.this, "createUserWithEmail:onComplete:"
                                        + task.isSuccessful(), Toast.LENGTH_SHORT).show();



                                // If sign in fails, display a message to the User. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in User can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(LoginAct.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    //Create a User and store it in firebase database
                                    FirebaseUser user = auth.getCurrentUser();
                                    String uid = user.getUid();

                                    NormalUser newUser = new NormalUser();
                                    Borrower newBorrower = new Borrower();
                                    Lender newLender = new Lender();

                                    mDatabase = FirebaseDatabase.getInstance().getReference();

                                    newUser.setEmail(email);
                                    newUser.setUid(uid);
                                    newUser.setPassword(password);
                                    newBorrower.setEmail(email);
                                    newBorrower.setUid(uid);
                                    newUser.setPassword(password);
                                    newLender.setEmail(email);
                                    newLender.setUid(uid);
                                    newUser.setPassword(password);



                                    newUser.setPassword(password);
                                    String key = uid;
                                    mDatabase.child("users").child(key).setValue(newUser);
                                    mDatabase.child("borrowers").child(key).setValue(newBorrower);
                                    mDatabase.child("lenders").child(key).setValue(newLender);

                                    Toast.makeText(LoginAct.this, "Authentication success!" + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }});

                //startActivity(new Intent(LoginAct.this, SignOutActivity.class));
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter User name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.i("test11","user11");

                //authenticate User
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginAct.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.i("test11","user2222");
                                Toast.makeText(getApplicationContext(), "login ", Toast.LENGTH_SHORT).show();

                                // If sign in fails, display a message to the User. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in User can be handled in the listener.
                                //progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();


                                } else {
                                    FirebaseUser user = auth.getCurrentUser();
                                    String uid = user.getUid();

                                    // On login button click, storing our username into normalUser,Lender Borrower classes.
                                    //Singleton Pattern implemented here
                                    NormalUser.Instance().setUid(uid);
                                    Borrower.Instance().setUid(uid);
                                    Lender.Instance().setUid(uid);



                                    Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();


                                    Intent intent = new Intent(LoginAct.this, HomePage.class);
                                    startActivity(intent);
                                    //finish();

                                    //Intent intent = new Intent(LoginAct.this, HomePage.class);
                                    //startActivity(intent);
                                    //finish();

                                }
                            }

                        });




            }

        });

    }

    /**
     * this method is defined for intent test
     * @return loggedin User id
     */
    public String returnCurrentUser(){
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();
        return uid;
    }
}
