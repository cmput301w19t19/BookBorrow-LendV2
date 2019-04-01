/*
 * Class CommentDetail.java
 *
 * Version 2.0
 *
 * Date 2019.4.1
 *
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
 */package com.example.y.bookborrow_lendv2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
import java.util.List;

public class CommentDetail extends AppCompatActivity {
    private String commonID;
    private String type;
    private ArrayList<comment> mDatas;
    private ArrayList<RatingAndComment> RatingAndCommentArrayList;
    private CommentAdapter mAdapter;
    private comment comment;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference DbRef = database.getReference();
    DatabaseReference ISBNRef = database.getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    //private static final File USER_ICON = new File(Environment.getExternalStorageDirectory(), "user_icon.jpg");
    private static final int CODE_PHOTO_REQUEST = 5;
    private static final int CODE_CAMERA_REQUEST = 6;
    private static final int CODE_PHOTO_CLIP = 7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);
        Intent intent = getIntent();
        commonID = intent.getStringExtra("id");
        type = intent.getStringExtra("type");
        ListView listView = (ListView)findViewById(R.id.comment_listview);

        if(type.equals("bookISBN")){
            // initial the comment here
            ISBNRef = database.getReference("bookISBN/" + commonID).child("RatingAndComment");
            ValueEventListener postListener2 = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                    if (dataSnapshot2.exists()) {
                        for (DataSnapshot ds : dataSnapshot2.getChildren()) {
                            final RatingAndComment com = ds.getValue(RatingAndComment.class);
                            final String c_rating = com.getRating();
                            final String c_userID = com.getID();
                            final String c_comment = com.getComment();
                            FirebaseDatabase o = FirebaseDatabase.getInstance();
                            DatabaseReference userRef = o.getReference("users/" + c_userID);
                            ValueEventListener postListener3 = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                                    if (dataSnapshot3.exists()) {
                                        final NormalUser user = dataSnapshot3.getValue(NormalUser.class);
                                        final String c_username = user.getName();
                                        Log.i("testUname",c_username);
                                        //add the image
                                        StorageReference imageRef = storageRef.child("user/"+c_userID+"/1.jpg");
                                        final long ONE_MEGABYTE = 10 * 1024 * 1024;
                                        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                Log.i("step","success1");
                                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                                user.setPhoto(bitmap);
                                                comment = new comment(c_username,c_userID, c_rating, c_comment);
                                                comment.setPhoto(bitmap);
                                                mDatas.add(comment);
                                                mAdapter.notifyDataSetChanged();
                                                //bookPhoto.setImageBitmap(bitmap);
                                            }

                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.i("Result","failed");
                                            }
                                        });
                                        //comment = new comment(c_username,c_userID, c_rating, c_comment);
                                        //mDatas.add(comment);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError3) {
                                    Log.w("load: OnCancelled", databaseError3.toException());
                                }
                            };
                            userRef.addValueEventListener(postListener3);

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError2) {
                    Log.w("load: OnCancelled", databaseError2.toException());
                }
            };
            ISBNRef.addValueEventListener(postListener2);


        }
        else if(type.equals("borrower")){
            Log.i("test comment","borrower else if ");
            // initial the comment here
            ISBNRef = database.getReference("borrowers/" + commonID).child("RatingAndComment");
            ValueEventListener postListener2 = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                    if (dataSnapshot2.exists()) {
                        for (DataSnapshot ds : dataSnapshot2.getChildren()) {
                            final RatingAndComment com = ds.getValue(RatingAndComment.class);
                            final String c_rating = com.getRating();
                            final String c_userID = com.getID();
                            final String c_comment = com.getComment();
                            FirebaseDatabase o = FirebaseDatabase.getInstance();
                            DatabaseReference userRef = o.getReference("users/" + c_userID);
                            ValueEventListener postListener3 = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                                    if (dataSnapshot3.exists()) {
                                        final NormalUser user = dataSnapshot3.getValue(NormalUser.class);
                                        final String c_username = user.getName();
                                        Log.i("testUname",c_username);
                                        // need to add the image
                                        StorageReference imageRef = storageRef.child("user/"+c_userID+"/1.jpg");
                                        final long ONE_MEGABYTE = 10 * 1024 * 1024;
                                        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                Log.i("step","success1");
                                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                                user.setPhoto(bitmap);
                                                comment = new comment(c_username,c_userID, c_rating, c_comment);
                                                comment.setPhoto(bitmap);
                                                mDatas.add(comment);
                                                mAdapter.notifyDataSetChanged();
                                                //bookPhoto.setImageBitmap(bitmap);
                                            }

                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.i("Result","failed");
                                            }
                                        });
                                        //comment = new comment(c_username,c_userID, c_rating, c_comment);
                                        //mDatas.add(comment);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError3) {
                                    Log.w("load: OnCancelled", databaseError3.toException());
                                }
                            };
                            userRef.addValueEventListener(postListener3);

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError2) {
                    Log.w("load: OnCancelled", databaseError2.toException());
                }
            };
            ISBNRef.addValueEventListener(postListener2);
        }
        else if(type.equals("owner")){
            Log.i("test comment","owner else if ");
            // initial the comment here
            ISBNRef = database.getReference("lenders/" + commonID).child("RatingAndComment");
            ValueEventListener postListener2 = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                    if (dataSnapshot2.exists()) {
                        for (DataSnapshot ds : dataSnapshot2.getChildren()) {
                            final RatingAndComment com = ds.getValue(RatingAndComment.class);
                            //RatingAndCommentArrayList.add(com);
                            final String c_rating = com.getRating();
                            final String c_userID = com.getID();
                            final String c_comment = com.getComment();
                            FirebaseDatabase o = FirebaseDatabase.getInstance();
                            DatabaseReference userRef = o.getReference("users/" + c_userID);
                            ValueEventListener postListener3 = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                                    if (dataSnapshot3.exists()) {
                                        final NormalUser user = dataSnapshot3.getValue(NormalUser.class);
                                        final String c_username = user.getName();
                                        Log.i("testUname",c_username);
                                        // need to add the image
                                        StorageReference imageRef = storageRef.child("user/"+c_userID+"/1.jpg");
                                        final long ONE_MEGABYTE = 10 * 1024 * 1024;
                                        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                Log.i("step","success1");
                                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                                user.setPhoto(bitmap);
                                                //mDatas.add(comment);
                                                comment = new comment(c_username,c_userID, c_rating, c_comment);

                                                comment.setPhoto(bitmap);
                                                mDatas.add(comment);
                                                mAdapter.notifyDataSetChanged();
                                                //bookPhoto.setImageBitmap(bitmap);
                                            }

                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.i("Result","failed");
                                            }
                                        });
                                        //comment = new comment(c_username,c_comment, c_rating, c_comment);
                                        //mDatas.add(comment);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError3) {
                                    Log.w("load: OnCancelled", databaseError3.toException());
                                }
                            };
                            userRef.addValueEventListener(postListener3);

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError2) {
                    Log.w("load: OnCancelled", databaseError2.toException());
                }
            };
            ISBNRef.addValueEventListener(postListener2);
        }

        mDatas = new ArrayList<comment>();
        mAdapter = new CommentAdapter(this, mDatas);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                comment c = mDatas.get(position);
                String uid = c.getID();
                Log.i("test commentDetail","uid"+uid);
                Intent i = new Intent(CommentDetail.this,SearchingUserDetail.class);
                i.putExtra("profileID",uid);
                i.putExtra("flag","0");
                //i.putExtra("type",type);
                Log.i("profileID","aaa");
                startActivity(i);
            }
        });


    }
}
