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
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.BitSet;

/**
 * After the user press the sign up button from loginAct page,
 * this page will be shown.
 * UserName, Email, password and phone are prompted but only email and password are neccesary.
 * If email and password are valid,
 * init the basic user, borrower and lender infor to firebase and jumpt to homepage.
 *
 * @see user
 * @see borrower
 * @see lender
 */
public class Register extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private EditText inputEmail, inputPassword, userName, phone;
    private Button button;
    private ImageView portrait;
    private int CODE_PHOTO_REQUEST = 5;
    private Bitmap photo;
    private Uri uri;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        portrait = findViewById(R.id.Userimage2);
        inputEmail = (EditText) findViewById(R.id.InputRegisterEmail);
        inputPassword = (EditText) findViewById(R.id.InputRegisterPassword);
        userName = (EditText) findViewById(R.id.InputName_);
        phone = (EditText) findViewById(R.id.InputPhone_);
        button = (Button) findViewById(R.id.ButtonDone);

        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (photo == null) {
                    Toast.makeText(Register.this, "Please upload image!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = inputEmail.getText().toString().trim();

                String password = inputPassword.getText().toString().trim();

                String username = userName.getText().toString().trim();

                String Phone = phone.getText().toString().trim();

                register(email,password,username,Phone);


            }});
        portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                //intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent,CODE_PHOTO_REQUEST);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(Register.this, "canceled", Toast.LENGTH_LONG).show();
            return;
        }
        if (requestCode == CODE_PHOTO_REQUEST) {
            if (Data != null) {
                //Bitmap photo = null;
                try {
                    uri = Data.getData();
                    photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    portrait.setImageBitmap(photo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void register(final String email,final String password, final String username, final String phone){

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
                            StorageReference storageReference = storageRef.child("user/" + uid + "/" + "1.jpg");
                            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                                }
                            });

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
