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

/**
 * This activity handles home_page, allow user select as borrower or owner view and edit profield and
 * log out
 * @version 1.0
 */
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class home_page extends AppCompatActivity {
    private FirebaseAuth auth;
    private TextView username;
    private DatabaseReference mDatabase;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private ImageView head;
    private ImageButton NA1;
    private ImageButton NA2;
    private ImageButton NA3;
    private ImageButton NA4;

    DatabaseReference DbRef = database.getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        auth = FirebaseAuth.getInstance();
        head = findViewById(R.id.UserHead);
        FirebaseUser user = auth.getCurrentUser();
        final String uid = user.getUid();
        NA1 = (ImageButton)findViewById(R.id.NA1);
        NA2 = (ImageButton)findViewById(R.id.NA2);
        NA3 = (ImageButton)findViewById(R.id.NA3);
        NA4 = (ImageButton)findViewById(R.id.NA4);

        final Intent intent1 = new Intent(home_page.this,SeeImageActivity.class);


        //get current logged in user name

        DbRef = database.getReference("users/"+uid);
        username = (TextView) findViewById(R.id.UserName);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //username = (TextView) findViewById(R.id.UserName);

                // Get Post object and use the values to update the UI
                NormalUser currentUser = dataSnapshot.getValue(NormalUser.class);


                String UserName = currentUser.getName();

                username.setText(UserName);
                StorageReference imageRef = storageRef.child("user/"+uid+"/1.jpg");
                final long ONE_MEGABYTE = 1024 * 1024;
                imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Log.i("Result","success");
                        intent1.putExtra("image",bytes);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        head.setImageBitmap(bitmap);
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Result","failed");
                    }
                });


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
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });

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

        NA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Already in home page",Toast.LENGTH_SHORT).show();
            }
        });

        NA2.setOnClickListener(new View.OnClickListener() {


            //hello
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, OwnerHomeActivity.class);
                startActivity(intent);
                //finish();

            }
        });

        NA3.setOnClickListener(new View.OnClickListener() {


            //hello
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, BorrowerMenu.class);
                startActivity(intent);
                //finish();

            }
        });

        NA4.setOnClickListener(new View.OnClickListener() {


            //hello
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, SearchingUserDetail.class);
                intent.putExtra("profileID",uid);
                startActivity(intent);
                //finish();

            }
        });








    }


}
