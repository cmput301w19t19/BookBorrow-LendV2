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
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
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

import java.util.ArrayList;

/**
 * This Class is to show all the detail of a book if the user want to know
 * when the user is seeing other activity.
 *
 * @author Team 19
 * @version 1.0
 * @see SearchResultForBook, RatingAndComment, CommentDetail
 * @see BorrowerRequest
 */
public class PublicBookDetails extends AppCompatActivity {

    private String bookid;
    private String flag;
    private String ownerID;
    private lender bookOwner;
    private String title1;
    private book requestedBook;
    private TextView see_more;
    private TextView bookNameTV;
    private TextView ISBNTV;
    private TextView bookAuthorTV;
    private TextView bookStateTV;
    private TextView bookRateTV;
    private TextView bookOwnerTV;
    private TextView DescriptionTV;
    private TextView bookDescriptionTV;
    //private TextView booktitle;
    private ImageView bookPhoto;
    private Button requestButton;
    //private Button returnButton;
    private Button locationButton;
    private book b;
    private String Keyword;
    private FirebaseAuth auth;
    private DatabaseReference r;
    private String ISBN;
    private ArrayList<comment> mDatas;
    private CommentAdapter mAdapter;
    private ListView listview;
    private comment comment;
    private  Bitmap photo;



    FirebaseDatabase database = FirebaseDatabase.getInstance();


    DatabaseReference DbRef = database.getReference();
    DatabaseReference ISBNRef = database.getReference();

    DatabaseReference dbRef = database.getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();


    private double latFromFirebase;  //location code from firebase
    private double longFromFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_book_details);

        Intent intent = getIntent();
        bookid = intent.getStringExtra("Id");
        Keyword = intent.getStringExtra("Keyword");
        flag = intent.getStringExtra("flag");
        see_more = (TextView)findViewById(R.id.public_see_more);
        //booktitle = findViewById(R.id.pBookDetialTitle);
        bookNameTV = (TextView)findViewById(R.id.puBookName);
        ISBNTV = (TextView)findViewById(R.id.puBookISBN);
        bookAuthorTV = (TextView)findViewById(R.id.puBookAuthor);
        bookStateTV = (TextView)findViewById(R.id.puBookState);
        bookRateTV = (TextView)findViewById(R.id.puBookRate);
        bookOwnerTV = (TextView)findViewById(R.id.puBookOwner);
        bookDescriptionTV = (TextView)findViewById(R.id.puBookDescription);
        DescriptionTV = (TextView)findViewById(R.id.comment);
        requestButton = (Button)findViewById(R.id.requestTheBook);
        //returnButton = (Button)findViewById(R.id.puReturnButton);
        locationButton = (Button)findViewById(R.id.pubBookLocation);
        bookPhoto = findViewById(R.id.bookPhoto1);
        FirebaseDatabase m = FirebaseDatabase.getInstance();
        r = m.getReference("book/"+bookid);
        final Intent intent1 = new Intent(PublicBookDetails.this, SeeImageActivity.class);

        requestButton.setVisibility(View.INVISIBLE);

        Log.i("flag0",flag);
        if (! flag.equals("BorrowerRequest" ) && ! flag.equals("BorrowBook" )) {
            Log.i("flag1",flag);

            requestButton.setVisibility(View.VISIBLE);
        }

        /**
         *  Get the information of the book from firebase and show them on the screen
         */
        ValueEventListener bookListner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    b = dataSnapshot.getValue(book.class);
                    Log.i("test22",b.getName());
                    bookNameTV.setText(b.getName());

                    ISBN = b.getISBN();
                    if (ISBN != null) {
                        ISBNTV.setText(ISBN);
                    }

                    String author = b.getAuthor();
                    if (author != null) {
                        bookAuthorTV.setText(author);
                    }

                    String state = b.getStatus();
                    if (state != null) {
                        bookStateTV.setText(state);
                    }

                    String rate = b.getBookRating();
                    bookRateTV.setText(rate);

                    String email = b.getOwnerEmail();
                    Log.i("test ownerEmail","hello"+email);
                    if (email != null){
                        bookOwnerTV.setText(email);
                    }

                    String description = b.getDescription();
                    if (description != null) {
                        bookDescriptionTV.setText(description);
                    }

                    StorageReference imageRef = storageRef.child("book/"+bookid+"/1.jpg");
                    final long ONE_MEGABYTE = 10 * 1024 * 1024;
                    imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Log.i("Result","success");
                            intent1.putExtra("image",bytes);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            photo = bitmap;
                            bookPhoto.setImageBitmap(bitmap);
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("Result","failed");
                        }
                    });

                    // initial the comment here
                    ISBNRef = database.getReference("bookISBN/" + ISBN).child("RatingAndComment");
                    ValueEventListener postListener2 = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                            if (!dataSnapshot2.exists()){
                                see_more.setVisibility(View.GONE);
                                listview.setVisibility(View.GONE);
                                DescriptionTV.setText("No one borrow it yet!");
                            }else{
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
                                                        comment = new comment(c_username,"", c_rating, c_comment);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Fail to get data from database",Toast.LENGTH_SHORT).show();

            }
        };
        r.addValueEventListener(bookListner);

        mDatas = new ArrayList<comment>();
        listview = (ListView) findViewById(R.id.ListViewForComment);
        mAdapter = new CommentAdapter(this, mDatas);
        listview.setAdapter(mAdapter);

        /**
         * If the user want to borrow the book, the user will click request button
         * set the requester id to the book requested list
         * set the book to the requester's requested list
         */
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b.getStatus().equals("accepted")||b.getStatus().equals("borrowed")){
                    Toast.makeText(getApplicationContext(),"can't be borrowed",Toast.LENGTH_SHORT).show();
                    return;
                }

                auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                DatabaseReference r2 = FirebaseDatabase.getInstance().getReference();
                String uid = user.getUid();
                r2.child("book").child(bookid).child("requestList").child(uid).setValue(true);
                r2.child("borrowers").child(uid).child("requestList").child(bookid).setValue(true);
                //add requested book to lender's requested list to keep track of the new requests
               // r2.child("lenders").child(ownerID).child("requestList").child(bookid).setValue(true);
                r2.child("book").child(bookid).child("status").setValue("requested");

                //set book status to requested


                //get book by bookid
                DbRef = database.getReference("book/"+bookid);



                  ValueEventListener postListener2 = new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                         // bookOwner = dataSnapshot.getValue(lender.class);
                          requestedBook = dataSnapshot.getValue(book.class);
                          //title1 = requestedBook.getID();

                          //Log.i("tttttttt","aaaaa"+title1);
                          ownerID = requestedBook.getOwnerID();


                          //retireve onwer by bookid
                          dbRef = database.getReference("lenders/"+ownerID);

                          ValueEventListener PostListener = new ValueEventListener() {
                              @Override
                              public void onDataChange(DataSnapshot dataSnapshot) {
                                  bookOwner = dataSnapshot.getValue(lender.class);
                              }
                              @Override
                              public void onCancelled(DatabaseError databaseError) {
                                  // Getting Post failed, log a message
                                  Log.w( "loadPost:onCancelled", databaseError.toException());
                              }
                          };

                          dbRef.addValueEventListener(PostListener);
                          Log.i("22222222","bbbbbbbb"+ownerID);

                          //add requested book id to book owner's ListOfNewRequests list
                          dbRef.child("ListOfNewRequests").
                                  child(bookid).child("requested").setValue(true);
                          dbRef.child("ListOfNewRequests").child(bookid).
                                  child("checkedByOwner").setValue(false);
                          dbRef.child("ListOfNewRequests").child(bookid).
                                  child("bookID").setValue(bookid);
                          Log.i("33333333","cccccccc");



                      }
                      @Override
                      public void onCancelled(DatabaseError databaseError) {
                          // Getting Post failed, log a message
                          Log.w( "loadPost:onCancelled", databaseError.toException());
                      }
                  };


                  DbRef.addValueEventListener(postListener2);
                  Log.i("1111111133333","aaaaaaaaa");



            }
        });
/*
        /**
         * Return to the activity which all this one, using flag to distinct caller
         */





        bookPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photo==null){
                    return;
                }
                else {
                    startActivity(intent1);
                }
            }
        });

        //borrower click location button than can view
        //this book's location on map
        //need to send latlong(pull back from firebase svae as locationCode)
        // to the map activity, and display this location code on map
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase m = FirebaseDatabase.getInstance();
                DatabaseReference r2 = m.getReference("book/"+bookid);

                ValueEventListener bookListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        book bookFromFirebase = dataSnapshot.getValue(book.class);
                        String status = bookFromFirebase.getStatus();
                        if (status.equals("available")|| status.equals("borrowed")||status.equals("requested")){
                            Log.w("if accepted/borrowed", "accepted/borrowed");

                            Toast.makeText(getApplicationContext(), "Book is not currently accepted, can't see location!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (bookFromFirebase.getLatitude() != null){
                            latFromFirebase = bookFromFirebase.getLatitude();
                        }

                        if (bookFromFirebase.getLongitude() != null){
                            longFromFirebase = bookFromFirebase.getLongitude();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),"Fail to renew data",Toast.LENGTH_SHORT).show();

                    }

                };

                LatLng locationCode = new LatLng(latFromFirebase,longFromFirebase);
                //Toast.makeText(getApplicationContext(),locationCode.toString(),Toast.LENGTH_SHORT).show();
                Intent location = new Intent(PublicBookDetails.this, MapsActivityBorrowerView.class);
                location.putExtra("locationCode",locationCode);
                startActivity(location);
                r2.addListenerForSingleValueEvent(bookListener);

            }
        });

        see_more.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.i("public book detail", "666");
                Intent intent = new Intent(PublicBookDetails.this, CommentDetail.class);
                intent.putExtra("id",ISBN);
                intent.putExtra("type","bookISBN");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed(){
        if(flag.equals("searchbook")) {

            Intent back = new Intent(PublicBookDetails.this, SearchResultForBook.class);
            back.putExtra("key",Keyword);
            startActivity(back);
        } else if (flag.equals("RateToOwner")) {
            Intent back = new Intent(PublicBookDetails.this,home_page.class);
            startActivity(back);
            finish();
        }else if (flag.equals("BorrowerRequest")){
            Intent back = new Intent(PublicBookDetails.this, BorrowerRequest.class);
            startActivity(back);
            finish();
        } else if (flag.equals("BorrowBook")){
            Intent back = new Intent(PublicBookDetails.this,BorrowBookList.class);
            startActivity(back);
            finish();
        } else if (flag.equals("homepage")){
            Intent back = new Intent(PublicBookDetails.this,home_page.class);
            startActivity(back);
            finish();
        }
        else{
            finish();
        }
    }
}
