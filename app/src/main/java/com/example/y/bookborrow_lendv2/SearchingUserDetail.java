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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static android.view.View.GONE;

/**
 *  This class display user basic infor,
 *  the rate and commment the user recieved as a lender and
 *  the rate and comment the user recieved as a borrower.
 *  The class will be invoked when user try to see detail inform after done searching for people.
 *  The class will allow users to see more comments if the number of comments are more than 1
 *  @see CommentDetail
 *  @see SearchResultForPeople
 *
 */
public class SearchingUserDetail extends AppCompatActivity {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private lender l;
    private borrower b;
    private NormalUser u;
    private String profileID;
    private comment comment;
    private comment comment1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_user_detail);

        final TextView userNameTextView = (TextView)findViewById(R.id.puBookName);
        final TextView userEmailTextView = (TextView)findViewById(R.id.searchUserDetailUserEmailInput);
        final TextView phoneNumberTextView = (TextView)findViewById(R.id.searchUserDetailPhoneInput);
        final ImageView userPhoto = (ImageView) findViewById(R.id.userPhoto);

        final TextView lendBookTimeTextView = (TextView)findViewById(R.id.searchUserDetaillendBookTimeINput);
        final TextView lenderRateTextView = (TextView)findViewById(R.id.searchUserDetaiBorrowerRating);
        final TextView lenderSeeMore = (TextView)findViewById(R.id.see_more2);
        final ListView lenderListView = (ListView)findViewById(R.id.ListViewOwner);
        final ArrayList<comment> lenderCommentList = new ArrayList<comment>();
        final CommentAdapter lenderCommentAdapter = new CommentAdapter(this,lenderCommentList);

        final TextView borrowBookTimeTextView = (TextView)findViewById(R.id.searchUserDetailBorrowerBookTimeInput);
        final TextView borrowRateTextView = (TextView)findViewById(R.id.searchUserDetailOwnerRating);
        final TextView borrowerSeeMore = (TextView)findViewById(R.id.see_more3);
        final ListView borrowerListView = (ListView)findViewById(R.id.ListViewBorrower);
        final ArrayList<comment> borrowerCommentList = new ArrayList<>();
        final CommentAdapter borrowerCommentAdapter = new CommentAdapter(this,borrowerCommentList);
        final Intent intent1 = new Intent(SearchingUserDetail.this,SeeImageActivity.class);
        Intent i = getIntent();
        profileID = i.getStringExtra("profileID");
        try{
            String flag = i.getStringExtra("flag");
            if (flag.equals("0")){
                borrowerSeeMore.setVisibility(GONE);
                lenderSeeMore.setVisibility(GONE);
            }
        }catch (Exception e){

        }
        Log.i("test userdetail","profileid"+profileID);

        StorageReference imageRef = storageRef.child("user/"+profileID+"/1.jpg");
        final long ONE_MEGABYTE = 10 * 1024 * 1024;
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Log.i("Result","success");
                intent1.putExtra("image",bytes);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                userPhoto.setImageBitmap(bitmap);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Result","failed");
            }
        });


        // get lender infor from firebase
        final DatabaseReference r1 = FirebaseDatabase.getInstance().getReference("lenders/"+profileID);
        ValueEventListener lenderListern = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    l = dataSnapshot.getValue(lender.class);
                    Integer lendBookTime = l.getLendBookTime();

                    //show the listview if there is at least a comment
                    //show the see more if there is more than one comment
                    if (lendBookTime != 0){
                        lendBookTimeTextView.setText(Integer.toString(lendBookTime));
                        lenderRateTextView.setText(l.getLenderRating());
                        DatabaseReference commentR1 =r1.child("RatingAndComment");
                        if(lendBookTime == 1){
                            lenderSeeMore.setVisibility(GONE);
                        }

                        // get comment item from the firebase
                        ValueEventListener lenderCommentListern = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                if (dataSnapshot2.exists()){
                                    for (DataSnapshot ds : dataSnapshot2.getChildren()) {
                                        final RatingAndComment com = ds.getValue(RatingAndComment.class);
                                        final String c_rating = com.getRating();
                                        final String c_userID = com.getID();
                                        final String c_comment = com.getComment();

                                        // get commenter's username
                                        FirebaseDatabase o = FirebaseDatabase.getInstance();
                                        DatabaseReference userRef = o.getReference("users/" + c_userID);
                                        ValueEventListener photoListern = new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                                                if (dataSnapshot3.exists()){
                                                    final NormalUser n = dataSnapshot3.getValue(NormalUser.class);
                                                    final String c_username = n.getName();

                                                    //comment = new comment(c_username,"", c_rating, c_comment);
                                                    //lenderCommentList.add(comment);
                                                    lenderCommentAdapter.notifyDataSetChanged();

                                                    //get commenter's photo
                                                    StorageReference imageRef = storageRef.child("user/" + c_userID + "/1.jpg");
                                                    final long ONE_MEGABYTE = 10 * 1024 * 1024;
                                                    imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                        @Override
                                                        public void onSuccess(byte[] bytes) {
                                                            Log.i("Result", "success");
                                                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                            comment = new comment(c_username,"", c_rating, c_comment);
                                                            comment.setPhoto(bitmap);
                                                            lenderCommentList.add(comment);
                                                            lenderCommentAdapter.notifyDataSetChanged();
                                                        }

                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.i("Result", "failed");
                                                        }
                                                    });
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        };
                                        userRef.addListenerForSingleValueEvent(photoListern);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError2) {

                            }
                        };
                        commentR1.addListenerForSingleValueEvent(lenderCommentListern);

                        // if there is no comment, do not show see more and list view
                    }else{
                        lenderRateTextView.setText("has not lent any book yet!");
                        lenderSeeMore.setVisibility(GONE);
                        lenderListView.setVisibility(GONE);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        r1.addListenerForSingleValueEvent(lenderListern);
        lenderListView.setAdapter(lenderCommentAdapter);

        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });

        lenderSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchingUserDetail.this, CommentDetail.class);
                intent.putExtra("id",profileID);
                intent.putExtra("type","owner");
                Log.i("test searchingUser","borrower see more click");
                startActivity(intent);
            }
        });


        // get borrower infor from firebase
        final DatabaseReference r2 = FirebaseDatabase.getInstance().getReference("borrowers/"+profileID);
        ValueEventListener borrowerListern = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    b = dataSnapshot.getValue(borrower.class);
                    Integer borrowBookTime = b.getborrowBookTime();

                    //show the listview if there is at least a comment
                    //show the see more if there is more than one comment
                    if (borrowBookTime == 0){
                        borrowBookTimeTextView.setText("Has not borrowed any book yet!");
                        borrowerSeeMore.setVisibility(GONE);
                        borrowerListView.setVisibility(GONE);

                    }else{
                        if(borrowBookTime == 1){
                            borrowerSeeMore.setVisibility(GONE);
                        }
                        borrowBookTimeTextView.setText(Integer.toString(borrowBookTime));
                        borrowRateTextView.setText(b.getBorrowerRating());

                        // get comment item from the firebase
                        DatabaseReference commentR2 =r2.child("RatingAndComment");
                        ValueEventListener photoListner = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                if(dataSnapshot2.exists()){
                                    for (DataSnapshot ds : dataSnapshot2.getChildren()) {
                                        final RatingAndComment com = ds.getValue(RatingAndComment.class);
                                        final String c_rating = com.getRating();
                                        final String c_userID = com.getID();
                                        final String c_comment = com.getComment();
                                        FirebaseDatabase o = FirebaseDatabase.getInstance();
                                        DatabaseReference userRef = o.getReference("users/" + c_userID);

                                        // get commenter's username
                                        ValueEventListener photoListern = new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                                                if (dataSnapshot3.exists()){
                                                    final NormalUser n = dataSnapshot3.getValue(NormalUser.class);
                                                    final String c_username = n.getName();

                                                    // get commenter's photo
                                                    StorageReference imageRef = storageRef.child("user/" + c_userID + "/1.jpg");
                                                    final long ONE_MEGABYTE = 10 * 1024 * 1024;
                                                    imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                        @Override
                                                        public void onSuccess(byte[] bytes) {
                                                            Log.i("Result", "success");
                                                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                            comment1 = new comment(c_username,"", c_rating, c_comment);

                                                            comment1.setPhoto(bitmap);
                                                            borrowerCommentList.add(comment1);
                                                            borrowerCommentAdapter.notifyDataSetChanged();
                                                        }

                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.i("Result", "failed");
                                                        }
                                                    });


                                                    borrowerCommentAdapter.notifyDataSetChanged();
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        };

                                        userRef.addListenerForSingleValueEvent(photoListern);
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError2) {

                            }

                        };
                        commentR2.addListenerForSingleValueEvent(photoListner);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        r2.addListenerForSingleValueEvent(borrowerListern);
        borrowerListView.setAdapter(borrowerCommentAdapter);

        // get user basic infor from firebase
        DatabaseReference r3 = FirebaseDatabase.getInstance().getReference("users/"+profileID);
        ValueEventListener userListern = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    u = dataSnapshot.getValue(NormalUser.class);

                    String email = u.getEmail();
                    if (email != null){
                        userEmailTextView.setText(email);
                    }

                    String phone = u.getPhone();
                    if (phone != null){
                        phoneNumberTextView.setText(phone);
                    }else{
                        phoneNumberTextView.setText("Phone is not set yet.");
                    }

                    String name = u.getName();
                    if (name != null){
                        userNameTextView.setText(name);
                    }else{
                        userNameTextView.setText("Name is not set yet!");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        r3.addListenerForSingleValueEvent(userListern);

        borrowerSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchingUserDetail.this, CommentDetail.class);
                intent.putExtra("id",profileID);
                intent.putExtra("type","borrower");
                Log.i("test searchingUser","borrower see more click");
                startActivity(intent);

            }
        });





    }
}
