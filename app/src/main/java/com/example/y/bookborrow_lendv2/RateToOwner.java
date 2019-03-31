/*
 * Copyright 2019 TEAM01
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

import static java.lang.Double.parseDouble;

/**
 * After a borrower return his borrowed book back from scan activity,
 * the borrower will see this activity
 * and be prompted to enter rate and comment for the lender and the borrowed book.
 * After finishing rating, the page will be back to PublicBookDetail.
 *
 * @see check_to_scan
 * @see PublicBookDetails
 */
public class RateToOwner extends AppCompatActivity {

    private book b;
    private bookISBN ISBN;
    private lender owner;
    private String uid;
    private String bid;
    private String lenderUid;
    private String bookISBNString;


    private EditText ownerRateEditText;
    private EditText ownerCommentEditText;
    private EditText bookRateEditText;
    private EditText bookCommentEditText;

    private TextView ownerUserEmailTextView;
    private TextView ownerUserNameTextView;
    private TextView bookNameTextView;
    private TextView bookAuthorTextView;
    private TextView bookISBNTextView;
    private ImageView bookImage;
    private ImageView ownerImage;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase m;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_to_owner);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uid = user.getUid();
        m = FirebaseDatabase.getInstance();

        ownerRateEditText = (EditText) findViewById(R.id.edit_owner_rate);
        ownerCommentEditText = (EditText) findViewById(R.id.edit_owner_comment);
        bookRateEditText = (EditText) findViewById(R.id.edit_book_rate);
        bookCommentEditText = (EditText) findViewById(R.id.edit_book_comment);
        bookImage = (ImageView)findViewById(R.id.Book_image);
        ownerImage = (ImageView)findViewById(R.id.Owner_image);
        Button saveButton = (Button)findViewById(R.id.RateToOwnerSaveButton);

        ownerUserNameTextView = (TextView) findViewById(R.id.Owner_username);
        ownerUserEmailTextView = (TextView)findViewById(R.id.Owner_user_email);
        bookNameTextView = (TextView) findViewById(R.id.Book_name);
        bookAuthorTextView = (TextView) findViewById(R.id.Book_author);
        bookISBNTextView = (TextView) findViewById(R.id.Book_ISBN);


        /**
         * get basic book information from firebase
         * set basic book information
         */
        Intent i = getIntent();
        bid = i.getStringExtra("bookID");
        Log.i("test RateToOwner","bookid"+bid);

        //String bid = "5f3a1368-57a6-470c-97f1-da74d90a4b9e";
        DatabaseReference r1 = m.getReference("book/"+bid);
        ValueEventListener bookLister = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    b = dataSnapshot.getValue(book.class);
                    String bookName = b.getName();

                    lenderUid = b.getOwnerID();
                    bookISBNString = b.getISBN();

                    if (bookName != null){
                        bookNameTextView.setText(bookName);
                    }

                    String bookISBN = b.getISBN();
                    if (bookISBN != null) {
                        bookISBNTextView.setText(bookISBN);
                    }

                    String author = b.getAuthor();
                    if (author != null) {
                        bookAuthorTextView.setText(b.getAuthor());
                    }
                    StorageReference imageRef = storageRef.child("book/"+bid+"/1.jpg");
                    final long ONE_MEGABYTE = 10 * 1024 * 1024;
                    imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Log.i("Result","success");
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            bookImage.setImageBitmap(bitmap);
                            b.setImage(bitmap);
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("Result","failed");
                        }
                    });


                    /**
                     * get bookISBN object from firebase
                     */
                    DatabaseReference r3 = m.getReference("bookISBN/" + b.getISBN());
                    ValueEventListener ISBNListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ISBN = dataSnapshot.getValue(bookISBN.class);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(),
                                    "Fail to get data from database",Toast.LENGTH_SHORT).show();
                        }
                    };
                    r3.addValueEventListener(ISBNListener);


                    /**
                     * get basic owner information from firebase
                     * set the information to the view
                     */
                    DatabaseReference r2 = m.getReference("lenders/" + lenderUid);
                    ValueEventListener lenderListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists() ){
                                owner = dataSnapshot.getValue(lender.class);

                                String userEmail = owner.getEmail();
                                if (userEmail != null){
                                    ownerUserEmailTextView.setText(userEmail);
                                }

                                String userName = owner.getName();
                                if (userName != null){
                                    ownerUserNameTextView.setText(userName);
                                }
                                StorageReference imageRef = storageRef.child("user/"+lenderUid+"/1.jpg");
                                final long ONE_MEGABYTE = 1024 * 1024;
                                imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        Log.i("Result","success");
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                        ownerImage.setImageBitmap(bitmap);
                                        owner.setPhoto(bitmap);
                                    }

                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.i("Result","failed");
                                    }
                                });

                                Log.i("test owner totalrate","hello"+owner.getLenderRating());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(),
                                    "Fail to get data from database",Toast.LENGTH_SHORT).show();
                        }
                    };
                    Log.i("testnn","888");
                    r2.addValueEventListener(lenderListener);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),
                        "Fail to get data from database",Toast.LENGTH_SHORT).show();
            }
        };

        r1.addValueEventListener(bookLister);
        Log.i("testnn","333");


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check necessary part get input
                String newOwnerRate = ownerRateEditText.getText().toString();
                if (TextUtils.isEmpty(newOwnerRate)){
                    Toast.makeText(getApplicationContext(),
                            "Enter rate to lender!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String newBookRate = bookRateEditText.getText().toString();
                if (TextUtils.isEmpty(newBookRate)){
                    Toast.makeText(getApplicationContext(),
                            "Enter rate to book!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // check input is valid
                Double newBookRatingDouble;
                Double newOwnerRatingDouble;
                try{
                    newBookRatingDouble = Double.parseDouble(newBookRate);
                    newOwnerRatingDouble = Double.parseDouble(newOwnerRate);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),
                            "A rate should be an number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (newBookRatingDouble < 0 || newBookRatingDouble > 10 ){
                    Toast.makeText(getApplicationContext(),
                            "A rate should be an number between 1 and 10!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (newOwnerRatingDouble < 0 || newOwnerRatingDouble > 10 ){
                    Toast.makeText(getApplicationContext(),
                            "A rate should be an number between 1 and 10!", Toast.LENGTH_SHORT).show();
                    return;
                }


                // set valid input for book into firebase
                String bookComment = bookCommentEditText.getText().toString();

                ISBN.setBookRate(newBookRatingDouble);

                RatingAndComment cBook = new RatingAndComment();
                cBook.setID(uid);
                cBook.setComment(bookComment);
                cBook.setRating(Double.parseDouble(newBookRate));
                ISBN.addNewComment(cBook);

                // set valid input for owner into firebase
                String ownerComment = ownerCommentEditText.getText().toString();

                owner.setLenderRating(newOwnerRatingDouble);

                RatingAndComment cOwner = new RatingAndComment();
                cOwner.setID(uid);
                cOwner.setComment(ownerComment);
                cOwner.setRating(newOwnerRatingDouble);
                owner.addNewComment(cOwner);

                Log.i("test RateToOwner","Just above start activity");

                //go back to public book detail
                Intent i = new Intent(RateToOwner.this,PublicBookDetails.class);
                i.putExtra("Id",bid);
                i.putExtra("flag","RateToOwner");
                startActivity(i);
                finish();


            }
        });

    }

}
