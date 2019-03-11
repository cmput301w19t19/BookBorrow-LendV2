package com.example.y.bookborrow_lendv2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class EditBookDetail extends AppCompatActivity {
    book b;
    EditText bookNamkeEditText;
    EditText authorEditText;
    EditText ISBNEditText;
    EditText descriptionEditText;
    String id;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book_detail);


        auth = FirebaseAuth.getInstance();



        Button saveButton = (Button)findViewById(R.id.Buttonsave);
        Button uploadButton = (Button)findViewById(R.id.ButtonUpload);


        bookNamkeEditText = (EditText)findViewById(R.id.pt4);
        authorEditText = (EditText)findViewById(R.id.pt2);
        ISBNEditText = (EditText)findViewById(R.id.pt3);
        descriptionEditText = (EditText)findViewById(R.id.editTextDes);
        final TextView bookstatusTextView = (TextView)findViewById(R.id.pt1);

        Intent i = getIntent();
        id = i.getStringExtra("0");
        Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();

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

                        String state = b.getStatus();
                        if (state != null) {
                            bookstatusTextView.setText(state);
                        }

                        String description = b.getDescription();
                        if (description != null) {
                            descriptionEditText.setText(b.getDescription());
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"Fail to get data from database",Toast.LENGTH_SHORT).show();
                }
            };
            r.addListenerForSingleValueEvent(bookLister);

        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.setName(bookNamkeEditText.getText().toString());
                b.setAuthor(authorEditText.getText().toString());
                b.setDescription(descriptionEditText.getText().toString());
                b.setISBN(ISBNEditText.getText().toString());

                FirebaseUser user = auth.getCurrentUser();
                DatabaseReference r = FirebaseDatabase.getInstance().getReference();

                String uid = user.getUid();
                r.child("lenders").child(uid).child("MyBookList").child(id).setValue(true);

                b.setToFirebase();
                String bookid = b.getID();
                Intent back = new Intent();
                back.putExtra("ID",bookid);
                setResult(1,back);
                finish();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upload = new Intent();
                String bookid = b.getID();
                upload.putExtra("bookid",bookid);
                Toast.makeText(getApplicationContext(),"The function is waiting for implemented",Toast.LENGTH_SHORT).show();
            }
        });



    }

}
