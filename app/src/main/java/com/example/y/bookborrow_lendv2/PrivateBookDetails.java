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
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.maps.model.LatLng;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This Class is to show all the detail of a book to the book owner
 *
 * @author team 19
 * @version1.0
 * @see MyBookList
 */
public class PrivateBookDetails extends AppCompatActivity {


    private String bookid;
    private TextView bookDetailTV;
    private TextView bookNameTV;
    private TextView ISBNTV;
    private TextView bookAuthorTV;
    private TextView bookStateTV;
    private TextView bookRateTV;
    private TextView bookDescriptionTV;
    private Button deleteButton;
    private Button editButton;
    private Button requestButton;
    private Button returnButton;
    private ImageView bookPhoto ;
    private String flag;

    private book bookx;
    private FirebaseAuth auth;
    private String ISBN;
    private ArrayList<comment> mDatas;
    private CommentAdapter mAdapter;
    private ListView listview;
    /**
     * The Database.
     */
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    /**
     * The Db ref.
     */
    DatabaseReference DbRef = database.getReference();
    DatabaseReference ISBNRef = database.getReference();
    /**
     * The Storage.
     */
    FirebaseStorage storage = FirebaseStorage.getInstance();
    /**
     * The Storage ref.
     */
    StorageReference storageRef = storage.getReference();
    //private static final File USER_ICON = new File(Environment.getExternalStorageDirectory(), "user_icon.jpg");
    private static final int CODE_PHOTO_REQUEST = 5;
    private static final int CODE_CAMERA_REQUEST = 6;
    private static final int CODE_PHOTO_CLIP = 7;
    /**
     * The Location button.
     */
    Button locationButton;

    private String locationCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_book_details);
        Intent intent = getIntent();
        bookid = intent.getStringExtra("Id");
        flag = intent.getStringExtra("flag");
        bookDetailTV = (TextView)findViewById(R.id.pBookDetialTitle);
        // bookNameTV contains the borrower name
        bookNameTV = (TextView)findViewById(R.id.pBookName);
        ISBNTV = (TextView)findViewById(R.id.pBookISBN);
        bookAuthorTV = (TextView)findViewById(R.id.pBookAuthor);
        bookStateTV = (TextView)findViewById(R.id.pBookState);
        bookRateTV = (TextView)findViewById(R.id.pBookRate);
        bookDescriptionTV = (TextView)findViewById(R.id.pBookDescription);
        deleteButton = (Button)findViewById(R.id.BookDetailDelete);
        editButton = (Button)findViewById(R.id.bookDetailEdit);
        requestButton = (Button)findViewById(R.id.bookDetailRequest);
        returnButton = (Button)findViewById(R.id.ReturnButton);
        bookPhoto = findViewById(R.id.bookPhoto);

        locationButton = (Button)findViewById(R.id.pBookLocation);


        /**
         *  Get the information of the book from firebase and show them on the screen
         */
        FirebaseDatabase m = FirebaseDatabase.getInstance();
        DatabaseReference r = m.getReference("book/"+bookid);

        ValueEventListener bookListner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    bookx = dataSnapshot.getValue(book.class);
                    bookDetailTV.setText(bookx.getName());

                    ISBN = bookx.getISBN();
                    if (ISBN != null) {
                        ISBNTV.setText(ISBN);
                        Log.i("testISBN","66666666666");
                    }

                    String author = bookx.getAuthor();
                    if (author != null) {
                        bookAuthorTV.setText(author);
                    }

                    String state = bookx.getStatus();
                    if (state != null) {
                        bookStateTV.setText(state);
                    }
                    if(state.equals("borrowed")){
                        final String bookID = bookx.getBorrowerID();
                        final DatabaseReference borrowerRef = database.getReference("borrowers/"+bookID);
                        ValueEventListener bookListner2 = new ValueEventListener(){
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                if (dataSnapshot2.exists()) {
                                    final borrower bor = dataSnapshot2.getValue(borrower.class);
                                    bookNameTV.setText(bor.getName());
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError2) {
                                Log.w("load: OnCancelled", databaseError2.toException());
                            }

                        };
                        borrowerRef.addListenerForSingleValueEvent(bookListner2);

                    }

                    String rate = bookx.getBookRating();
                    bookRateTV.setText(rate);

                    String description = bookx.getDescription();
                    if (description != null) {
                        bookDescriptionTV.setText(description);
                    }

                    StorageReference imageRef = storageRef.child("book/"+bookid+"/1.jpg");
                    final long ONE_MEGABYTE = 10 * 1024 * 1024;
                    imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Log.i("Result", "success");
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            bookPhoto.setImageBitmap(bitmap);
                            bookx.setImage(bitmap);
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("Result", "failed");
                        }
                    });

                    // initial the comment here
                    ISBNRef = database.getReference("bookISBN/" + ISBN).child("RatingAndComment");
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
                                                comment comment = new comment(c_username,"", c_rating, c_comment);
                                                mDatas.add(comment);
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
            public void onCancelled(@NonNull DatabaseError databaseError1) {
                Log.w("load: OnCancelled", databaseError1.toException());
            }
        };
        r.addListenerForSingleValueEvent(bookListner);

        // set the adapter of comment
        mDatas = new ArrayList<comment>();
        listview = (ListView) findViewById(R.id.c_comment);
        mAdapter = new CommentAdapter(this, mDatas);
        listview.setAdapter(mAdapter);





        /**
         * Prompt the user to edit book detail if the user want
         * Go to editBookDetail
         * @see editBookDetail
         */
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PrivateBookDetails.this,EditBookDetail.class);
                i.putExtra("0",bookid);
                startActivityForResult(i,2);
            }
        });

        /**
         *Prompt the user to delete the book if the user does not want to share it
         * remove the book from the book list on firebase
         * remove the book from the owner book list firebase
         */
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookx.deleteFromFirebase();
                auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                DatabaseReference r = FirebaseDatabase.getInstance().getReference();
                String uid = user.getUid();
                r.child("lenders").child(uid).child("MyBookList").child(bookid).setValue(null,null);
                Intent i = new Intent(PrivateBookDetails.this,MyBookList.class);
                startActivity(i);

            }
        });

        /**
         * Prompt the user to see all the requests on the book
         * go to requestToOwner activity
         * @see requestToOwner
         */
        requestButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(PrivateBookDetails.this,RequestToOwner.class);
                i.putExtra("request",bookid);
                startActivityForResult(i,3);
            }
        });

        /**
         * Return to the activity which all this one, using flag to distinct caller
         */
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PrivateBookDetails.this,MyBookList.class);
                startActivity(i);
            }
        });


        /*takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(USER_ICON));
                Log.i("666","66666");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, CODE_CAMERA_REQUEST);
                }
                //startActivityForResult(intent,CODE_CAMERA_REQUEST);
                Log.i("666","77777");
            }
        });
*/
        /*gallery.setOnClickListener(new View.OnClickListener() {
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
        */

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(getApplicationContext(), MapsActivityOwnerSetLocation.class);
                //startActivityForResult(new Intent(PrivateBookDetails.this, MapsActivityOwnerSetLocation.class), 4);
                pickPointOnMap();

            }
        });
    }
/*
    private void photoClip(Uri uri) {
        Intent intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CODE_PHOTO_CLIP);
    }

    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            bookPhoto.setImageBitmap(photo);
        }
    }
    */
    @Override
    public void onBackPressed(){
        //Intent intent = new Intent();
        //setResult(RESULT_OK,intent);
        //finish();
        if (flag.equals("MyBooks")){
            Intent intent = new Intent (PrivateBookDetails.this, MyBookList.class);
            startActivity(intent);
        } else if (flag.equals("View")){
            Intent intent = new Intent (PrivateBookDetails.this, ViewRequests.class);
            startActivity(intent);
        }




    }

    /**
     * The constant pickMapPointRequest.
     */
//switch to map activity, user can pick a point on map
    //map activity will return lat and long
    static final int pickMapPointRequest = 100;
    private void pickPointOnMap(){
        Intent pickPointIntent = new Intent(this,MapsActivityOwnerSetLocation.class);
        startActivityForResult(pickPointIntent, pickMapPointRequest);
    }



    /**
     *  Get all result back
     * @param requestCode
     * @param resultCode
     * @param Data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data){
        super.onActivityResult(requestCode,resultCode,Data);
        //ImageView bookPhoto  = findViewById(R.id.bookPhoto);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(PrivateBookDetails.this, "canceled", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CODE_CAMERA_REQUEST:
                Bundle extras = Data.getExtras();
                if(extras == null){
                    Log.i("not null","true");
                }
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                bookPhoto.setImageBitmap(imageBitmap);
                /*Uri uri1 = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),imageBitmap, null,null));
                StorageReference storageReference1 = storageRef.child("book/"+bookid+"/"+"1.jpg");
                storageReference1.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                    }
                }); */
                break;
                /*if (USER_ICON.exists()) {
                    Log.i("555","555555");
                    //photoClip(Uri.fromFile(USER_ICON));
                }*/

            case CODE_PHOTO_REQUEST:
                if (Data != null) {
                    Bitmap photo = null;
                    try {
                        Uri uri = Data.getData();
                        Log.i("hello22","22");
                        //if (extra != null) {
                        //  Log.i("hello22","slslsl");
                        photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        bookPhoto.setImageBitmap(photo);
                        StorageReference storageReference = storageRef.child("book/"+bookid+"/"+"1.jpg");
                        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                            }
                        });
                        /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        //photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        //byte[] data = baos.toByteArray();
                        //UploadTask uploadTask = storageRef.putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PrivateBookDetails.this,"upload failed",Toast.LENGTH_SHORT).show();
                                Log.i("upload??","upload failed");
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(PrivateBookDetails.this,"upload succeeded",Toast.LENGTH_SHORT).show();
                                Log.i("upload??","upload failed");
                            }
                        }); */
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    //setImageToHeadView(Data);
                    //photoClip(Data.getData());
                }
                break;
            /*case CODE_PHOTO_CLIP:
                if (Data != null) {
                    setImageToHeadView(Data);
                }
                break;
                */
        }


        if (requestCode == 2 && resultCode == 1){
            bookid = Data.getStringExtra("ID");
        }

        if (requestCode == 3){
        }


        /*return from map activity and
        toast the location user long click in map view activity
         */
        if (requestCode == pickMapPointRequest){
            LatLng latLng = (LatLng) Data.getParcelableExtra("picked_point");


            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference r = db.getReference("book/"+bookx.getID());
            r.child("longitude").setValue(latLng.longitude);
            r.child("latitude").setValue(latLng.latitude);
            //Toast.makeText(this, "Book Location Saved: " + latLng.latitude + " " + latLng.longitude, Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Book Location Saved!", Toast.LENGTH_LONG).show();

        }



        FirebaseDatabase m = FirebaseDatabase.getInstance();
        DatabaseReference r = m.getReference("book/"+bookid);

        ValueEventListener bookListner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                bookx = dataSnapshot.getValue(book.class);

                String bookName;
                if (bookx.getName() != null){
                    bookNameTV.setText(bookx.getName());
                }
                String ISBN = bookx.getISBN();
                if (ISBN != null){
                    ISBNTV.setText(ISBN);
                }

                String author = bookx.getAuthor();
                if (author != null){
                    bookAuthorTV.setText(ISBN);
                }

                String state = bookx.getStatus();
                if (state != null){
                    bookStateTV.setText(state);
                }

                String rate = bookx.getBookRating();
                bookRateTV.setText(rate);

                String description = bookx.getDescription();
                if (description != null){
                    bookDescriptionTV.setText(ISBN);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Fail to renew data",Toast.LENGTH_SHORT).show();

            }
        };

        r.addListenerForSingleValueEvent(bookListner);


    }



}