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
import android.provider.MediaStore;
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
    private TextView see_more;
    private Button deleteButton;
    private Button editButton;
    private Button requestButton;
    private ImageView bookPhoto ;
    private String flag;
    private Bitmap photo;

    private book bookx;
    private FirebaseAuth auth;
    private String ISBN;
    private ArrayList<comment> mDatas;
    private CommentAdapter mAdapter;
    private ListView listview;
    private comment comment;
    private Boolean bookExist;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference DbRef = database.getReference();
    DatabaseReference ISBNRef = database.getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    //private static final File USER_ICON = new File(Environment.getExternalStorageDirectory(), "user_icon.jpg");
    private static final int CODE_PHOTO_REQUEST = 5;
    private static final int CODE_CAMERA_REQUEST = 6;
    private static final int CODE_PHOTO_CLIP = 7;

    Button locationButton;

    private String locationCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_book_details);

        Intent intent = getIntent();
        bookid = intent.getStringExtra("Id");
        flag = intent.getStringExtra("flag");
        
        see_more = (TextView)findViewById(R.id.see_more);
        bookDetailTV = (TextView)findViewById(R.id.puBookName);
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
        bookPhoto = findViewById(R.id.bookPhoto);
        final Intent intent1 = new Intent(PrivateBookDetails.this,SeeImageActivity.class);
        locationButton = (Button)findViewById(R.id.pBookLocation);

        final Intent mapIntent = getIntent();

        /**
         *  Get the information of the book from firebase and show them on the screen
         */
        FirebaseDatabase m = FirebaseDatabase.getInstance();
        DatabaseReference r = m.getReference("book/"+bookid);
        bookExist = true;

        ValueEventListener bookListner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // if (bookExist == true){
                if (dataSnapshot.exists()) {
                    bookx = dataSnapshot.getValue(book.class);
                    bookDetailTV.setText(bookx.getName());

                    ISBN = bookx.getISBN();
                    if (ISBN != null) {
                        ISBNTV.setText(ISBN);
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
                                    bookNameTV.setText(bor.getEmail());
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
                            intent1.putExtra("image",bytes);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            bookPhoto.setImageBitmap(bitmap);
                            photo = bitmap;
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
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                            if (!dataSnapshot3.exists()){
                                Log.i("test","Jiuda");
                            }
                            if (dataSnapshot3.exists()) {
                                for (DataSnapshot ds : dataSnapshot3.getChildren()) {
                                    final RatingAndComment com = ds.getValue(RatingAndComment.class);
                                    final String c_rating = com.getRating();
                                    final String c_userID = com.getID();
                                    final String c_comment = com.getComment();
                                    FirebaseDatabase o = FirebaseDatabase.getInstance();
                                    DatabaseReference userRef = o.getReference("users/" + c_userID);
                                    ValueEventListener postListener3 = new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot4) {
                                            if (dataSnapshot4.exists()) {
                                                final NormalUser user = dataSnapshot4.getValue(NormalUser.class);
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
                }}
            //}
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



        bookPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photo == null){
                    return;
                } else {
                    startActivity(intent1);
                }
            }
        });

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

                String status = bookx.getStatus();
                if (status.equals("accepted")|| status.equals("borrowed")){
                    Log.w("if accepted/borrowed", "accepted/borrowed");

                    Toast.makeText(getApplicationContext(), "Book is borrowed or to be borrowed, can't delete now!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (status.equals("available") || status.equals("requested")){
                    Log.w("else", "start delete");
                    bookExist =false;
                    bookx.deleteFromFirebase();
                    String owner = bookx.getOwnerID();
                    String bookID = bookx.getID();
                    DatabaseReference requestRef = database.getReference("lenders").child(owner).child("ListOfNewRequests").child(bookID).
                            child("checkedByOwner");
                    requestRef.setValue("true");
                    //deletetBookRequest(bookx);
                    Log.w("test", "delete request functionrun");
                    StorageReference Sref = storage.getReference();
                    Sref.child("book").child(bookid).delete();
                    auth = FirebaseAuth.getInstance();
                    FirebaseUser user = auth.getCurrentUser();
                    DatabaseReference r = FirebaseDatabase.getInstance().getReference();
                    String uid = user.getUid();
                    r.child("lenders").child(uid).child("MyBookList").child(bookid).removeValue();
                    //r.child("lenders").child(uid).child("MyBookList").child(bookid).setValue(null,null);
                    Intent i = new Intent(PrivateBookDetails.this,MyBookList.class);


                    startActivity(i);
                }


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




        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(getApplicationContext(), MapsActivityOwnerSetLocation.class);
                //startActivityForResult(new Intent(PrivateBookDetails.this, MapsActivityOwnerSetLocation.class), 4);
                pickPointOnMap();

            }
        });

        see_more.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(PrivateBookDetails.this, CommentDetail.class);
                intent.putExtra("id",ISBN);
                intent.putExtra("type","bookISBN");
                startActivity(intent);
            }
        });


        /**
         * get location code from map Set location activity
         */


/*
        LatLng latLng = (LatLng) mapIntent.getParcelableExtra("picked_point");
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference rMap = db.getReference("book/"+bookx.getID());
        rMap.child("longitude").setValue(latLng.longitude);
        rMap.child("latitude").setValue(latLng.latitude);

        Toast.makeText(this, "Book Location Saved!", Toast.LENGTH_LONG).show();
*/

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
        if (flag.equals("MyBooks")||flag.equals("scan") ){
            Intent intent = new Intent (PrivateBookDetails.this, MyBookList.class);
            startActivity(intent);
        } else if (flag.equals("View")){
            Intent intent = new Intent (PrivateBookDetails.this, ViewRequests.class);
            startActivity(intent);
        } else if(flag.equals("RateToBorrower")){
            Intent intent = new Intent(PrivateBookDetails.this,home_page.class);
            startActivity(intent);
            finish();
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

   /** public void deletetBookRequest(book selectedBook){
        final String ownerID = selectedBook.getOwnerID();
        final String bookID = selectedBook.getID();



       final FirebaseDatabase m = FirebaseDatabase.getInstance();
        DatabaseReference r = m.getReference("lenders/"+ownerID+"/ListOfNewRequests");

        ValueEventListener bookListner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    final String storedBookID = ds.getKey();
                    if (storedBookID == bookID){
                        Log.i("test","if statement");

                        DatabaseReference R = m.getReference("lenders/"+ownerID+"/ListOfNewRequests/"+bookID);
                        R.removeValue();


                    }
                    else{
                        Log.i("test","else statement");

                    }

                }

               }
             @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Fail to delete book request data",Toast.LENGTH_SHORT).show();

            }

    };
        r.addListenerForSingleValueEvent(bookListner);
    }*/



}