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
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class RateToBorrower extends AppCompatActivity {

    private borrower borrowerx;
    private TextView borrowerNameTextView;
    private TextView borrowerEmailTextView;
    private Button saveButton;
    private EditText borrowerRateEditText;
    private EditText borrrowerCommentEditText;
    private ImageView borrowerImage;
    private String uid;
    private String bookID;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase m;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_to_borrower);
        borrowerImage = findViewById(R.id.Borrower_image);
        borrowerNameTextView = (TextView) findViewById(R.id.RateToBorrowerUserName);
        borrowerEmailTextView = (TextView) findViewById(R.id.RateToBorrowerUserEmail);
        borrowerRateEditText = (EditText) findViewById(R.id.edit_borrower_rate);
        borrrowerCommentEditText = (EditText) findViewById(R.id.edit_borrower_comment);
        saveButton = (Button)findViewById(R.id.RateToBorrowerSaveButton);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uid = user.getUid();
        m = FirebaseDatabase.getInstance();

        // need to get the borrower id from the last activity
        Intent i = getIntent();
        String bid = i.getStringExtra("borrowerID");
        bookID = i.getStringExtra("bookID");

        Log.i("test RateToBorrower","bookid"+bookID);

        //String bid = "J0WloTnZcAcds7lT7dCR9PtzH5x2";
        //bookID = "55c1c2d9-e85b-4753-88f7-e20b8a7a0f19";
        DatabaseReference r3 = m.getReference("borrowers/" + bid);
        ValueEventListener borrowerListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                borrowerx = dataSnapshot.getValue(borrower.class);
                borrowerNameTextView.setText(borrowerx.getName());
                borrowerEmailTextView.setText(borrowerx.getEmail());
                StorageReference imageRef = storageRef.child("user/"+uid+"/1.jpg");
                final long ONE_MEGABYTE = 10 * 1024 * 1024;
                imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Log.i("Result","success");
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        borrowerImage.setImageBitmap(bitmap);
                        borrowerx.setPhoto(bitmap);
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Result","failed");
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),
                        "Fail to get data from database",Toast.LENGTH_SHORT).show();
            }
        };
        r3.addValueEventListener(borrowerListener);



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newborrowerRate = borrowerRateEditText.getText().toString();
                if (TextUtils.isEmpty(newborrowerRate)){
                    Toast.makeText(getApplicationContext(),
                            "Enter rate to lender!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Double newBorrowerRatingDouble;
                try{
                    newBorrowerRatingDouble = Double.parseDouble(newborrowerRate);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),
                            "A rate should be an number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (newBorrowerRatingDouble < 0 || newBorrowerRatingDouble > 10 ){
                    Toast.makeText(getApplicationContext(),
                            "A rate should be an number between 1 and 10!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String BorrowerComment = borrrowerCommentEditText.getText().toString();
                RatingAndComment cOwner = new RatingAndComment();
                cOwner.setID(uid);
                cOwner.setComment(BorrowerComment);
                cOwner.setRating(newBorrowerRatingDouble);
                borrowerx.addNewComment(cOwner);
                borrowerx.setBorrowerRating(newBorrowerRatingDouble);

                Log.i("test RateToBorrower","just above start activity");

                Intent i = new Intent(RateToBorrower.this,PrivateBookDetails.class);
                i.putExtra("Id",bookID);
                startActivity(i);

            }
        });








    }

}
