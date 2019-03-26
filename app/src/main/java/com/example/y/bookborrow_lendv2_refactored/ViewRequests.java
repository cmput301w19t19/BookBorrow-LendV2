package com.example.y.bookborrow_lendv2_refactored;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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


import java.util.ArrayList;


/**
 * The type View requests.
 */
public class ViewRequests extends AppCompatActivity {

    private FirebaseAuth auth;
    private bookAdapter myBookAdapter;
    private ListView BookListView2;
    private ListView BookListView;
    private Button backButton;
    /**
     * The Database.
     */
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    /**
     * The Db ref.
     */
    DatabaseReference DbRef = database.getReference();
    /**
     * The Db ref.
     */
    DatabaseReference dbRef = database.getReference();

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private ArrayList<book> bookList = new ArrayList<>();
    private ArrayList<String> bookIDList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        Log.i("testnn","111111");

        setContentView(R.layout.activity_view_requests);
        backButton = findViewById(R.id.button3);
        BookListView = (ListView) findViewById(R.id.BookList);





        Log.i("testnn","2222222");

        FirebaseUser user = auth.getCurrentUser();
        final String uid = user.getUid();
        DatabaseReference rootRef = database.getReference("lenders").child(uid).child("ListOfNewRequests");

        Log.i("testnn","222");

        final ValueEventListener eventListener = new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.i("testnn","333");

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    final String bookID = ds.getKey();
                    DatabaseReference dbRef = database.getReference("lenders").child(uid).child("ListOfNewRequests").child(bookID);
                    ValueEventListener eventListener2 = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                                if (ds1.getKey().equals("checkedByOwner")) {
                                    if (ds1.getValue().equals(false)) {
                                        bookIDList.add(bookID);


                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    dbRef.addValueEventListener(eventListener2);
                }

                DbRef = database.getReference("book");



                ValueEventListener eventListener1 = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        for (DataSnapshot ds : dataSnapshot1.getChildren()) {
                            for (String bookID1 : bookIDList) {
                                if (ds.getKey().equals(bookID1)) {
                                    final book targetBook = ds.getValue(book.class);
                                    StorageReference imageRef = storageRef.child("book/"+bookID1+"/1.jpg");
                                    final long ONE_MEGABYTE = 1024 * 1024;
                                    imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                        @Override
                                        public void onSuccess(byte[] bytes) {
                                            Log.i("step","success1");
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                            targetBook.setImage(bitmap);
                                            bookList.add(targetBook);
                                            myBookAdapter.notifyDataSetChanged();
                                            //bookPhoto.setImageBitmap(bitmap);
                                        }

                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.i("Result","failed");
                                        }
                                    });
                                    bookList.add(targetBook);
                                    Log.i("testnn","444"+Integer.toString(bookList.size()));

                                }
                            }
                            //book targetBook = dataSnapshot1.getValue(book.class);


                             myBookAdapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError1) {

                    }
                };
                DbRef.addValueEventListener(eventListener1);


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };


        BookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Implement this method
                book bookItem = bookList.get(position);
                String clickedBookId = bookIDList.get(position);
                Toast.makeText(ViewRequests.this,bookItem.getAuthor(),Toast.LENGTH_SHORT).show();
                String bookId = bookItem.getID();
                Intent intent = new Intent(ViewRequests.this, PrivateBookDetails.class);
                intent.putExtra("Id", bookId);

                //记得把ViewRequests 里的OnItemCliskListener 点过的 request 的checkByOwner 改成 true，就没有小红点
                //dbRef = database.getReference("lenders").child(uid).child("ListOfNewRequests").child(clickedBookId).
                //child("checkedByOwner");
                //dbRef.setValue("true");


                startActivity(intent);
            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {


            //hello
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRequests.this, OwnerHomeActivity.class);
                startActivity(intent);
                //finish();

            }
        });


        //Log.i("testsize3",Integer.toString(booksID.size()));


        rootRef.addListenerForSingleValueEvent(eventListener);



        myBookAdapter = new bookAdapter(this, bookList);
        BookListView.setAdapter(myBookAdapter);







    }



}


