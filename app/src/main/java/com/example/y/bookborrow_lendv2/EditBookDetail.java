package com.example.y.bookborrow_lendv2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


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

/**
 * This class is to provide an interface for owner to edit details of one of his book
 *
 * @author Team 19
 * @version 1.0
 * @see PrivateBookDetails
 */
public class EditBookDetail extends AppCompatActivity {



    private book b;
    private EditText bookNamkeEditText;
    private EditText authorEditText;
    private EditText ISBNEditText;
    private EditText descriptionEditText;
    private ImageView bookPhoto;
    private String id;
    private Bitmap photo;


    Button ISBNButton;

    private FirebaseAuth auth;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private static final int CODE_PHOTO_REQUEST = 5;


    /**
     * Usage one:
     * Get a book id from PrivateBookDetails and show current information of a book to owner
     * Prompt owner to edit book information and save it
     * Usage two:
     * Get a flag (int 0) from MyBookList and prompt owner to enter information of a book
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book_detail);

        auth = FirebaseAuth.getInstance();
        Button saveButton = (Button)findViewById(R.id.Buttonsave);
        Button uploadButton = (Button)findViewById(R.id.ButtonUpload);
        ISBNButton = (Button)findViewById(R.id.ISBNscan);

        bookNamkeEditText = (EditText)findViewById(R.id.pt4);
        authorEditText = (EditText)findViewById(R.id.pt2);
        ISBNEditText = (EditText)findViewById(R.id.pt3);
        descriptionEditText = (EditText)findViewById(R.id.editTextDes);
        bookPhoto = findViewById(R.id.BookPhoto);



        Intent i = getIntent();
        id = i.getStringExtra("0");

        if (id.equals("0")){
            b = new book();
            id = b.getID();
        }else{

            FirebaseDatabase m = FirebaseDatabase.getInstance();
            DatabaseReference r = m.getReference("book/"+id);

            ValueEventListener bookLister = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        b = dataSnapshot.getValue(book.class);
                        b.setID(id);

                        String bookName = b.getName();
                        if (bookName != null){
                            bookNamkeEditText.setText(b.getName());
                        }

                        String ISBN = b.getISBN();
                        if (ISBN != null) {
                            ISBNEditText.setText(b.getISBN());
                        }

                        String author = b.getAuthor();
                        if (author != null) {
                            authorEditText.setText(b.getAuthor());
                        }

                        String description = b.getDescription();
                        if (description != null) {
                            descriptionEditText.setText(b.getDescription());
                        }
                        StorageReference imageRef = storageRef.child("book/"+id+"/1.jpg");
                        final long ONE_MEGABYTE = 10*1024 * 1024;
                        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Log.i("Result","success");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                b.setImage(bitmap);
                                photo = bitmap;
                                bookPhoto.setImageBitmap(bitmap);
                                //bookPhoto.setImageBitmap(bitmap);
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("Result","failed");
                            }
                        });


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"Fail to get data from database",Toast.LENGTH_SHORT).show();
                }
            };
            r.addListenerForSingleValueEvent(bookLister);

        }

        // scan the bar code to get the ISBN automatically
        ISBNButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(EditBookDetail.this)
                        .setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)//any type of ISBN
                        .setOrientationLocked(true)
                        .setPrompt("Please Point to QR code")
                        .setCameraId(0) //chose camera
                        .initiateScan();

            }
        });

        /**
         * get all the information from users
         * update book infor to firebase, path:booklendborrow/book/[bookid]
         * update owner book list to firebase, path:booklendborrow/[user id]
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (photo == null) {
                    Toast.makeText(EditBookDetail.this, "Please upload image!", Toast.LENGTH_SHORT).show();
                    return;
                }
                */
                b.setName(bookNamkeEditText.getText().toString());
                b.setAuthor(authorEditText.getText().toString());
                b.setDescription(descriptionEditText.getText().toString());
                b.setISBN(ISBNEditText.getText().toString());

                bookISBN ISBN = new bookISBN(ISBNEditText.getText().toString());
                ISBN.setToFirebase();


                FirebaseUser user = auth.getCurrentUser();
                DatabaseReference r = FirebaseDatabase.getInstance().getReference();

                String email = user.getEmail();
                b.setOwnerEmail(email);
                String uid = user.getUid();
                b.setOwnerID(uid);
                r.child("lenders").child(uid).child("MyBookList").child(id).setValue(true);

                b.setToFirebase();
                String bookid = b.getID();
                Intent back = new Intent(EditBookDetail.this,MyBookList.class);
                startActivity(back);
                //back.putExtra("ID",bookid);
                //setResult(1,back);
                //finish();
            }
        });

        /**
         * Go to upload image activity
         * will be finished in version2
         */

        uploadButton.setOnClickListener(new View.OnClickListener() {
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
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, Data);
        if (scanResult != null) {
            String result = scanResult.getContents();
            ISBNEditText.setText(result);
        }
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(EditBookDetail.this, "canceled", Toast.LENGTH_LONG).show();
            return;
        }
        if (requestCode == CODE_PHOTO_REQUEST) {
            if (Data != null) {
                //Bitmap photo = null;
                try {
                    Uri uri = Data.getData();
                    Log.i("hello22", "22");
                    //if (extra != null) {
                    //  Log.i("hello22","slslsl");
                    photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    bookPhoto.setImageBitmap(photo);
                    StorageReference storageReference = storageRef.child("book/" + id + "/" + "1.jpg");
                    storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
