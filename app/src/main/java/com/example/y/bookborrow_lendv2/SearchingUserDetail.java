package com.example.y.bookborrow_lendv2;

import android.content.Intent;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.view.View.GONE;

public class SearchingUserDetail extends AppCompatActivity {

    lender l;
    borrower b;
    NormalUser u;
    String profileID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_user_detail);

        final TextView userNameTextView = (TextView)findViewById(R.id.pBookDetialTitle);
        final TextView userEmailTextView = (TextView)findViewById(R.id.searchUserDetailUserEmailInput);
        final TextView phoneNumberTextView = (TextView)findViewById(R.id.searchUserDetailPhoneInput);

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



        Intent i = getIntent();
        profileID = i.getStringExtra("profileID");

        //profileID = "TUM3OMZKJuch4R3qj7a6oYi1WeO2";

        // get lender infor from firebase
        final DatabaseReference r1 = FirebaseDatabase.getInstance().getReference("lenders/"+profileID);
        ValueEventListener lenderListern = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    l = dataSnapshot.getValue(lender.class);
                    Integer lendBookTime = l.getLendBookTime();

                    if (lendBookTime != 0){
                        lendBookTimeTextView.setText(Integer.toString(lendBookTime));
                        lenderRateTextView.setText(l.getLenderRating());
                        DatabaseReference commentR1 =r1.child("RatingAndComment");
                        if(lendBookTime == 1){
                            lenderSeeMore.setVisibility(GONE);
                        }


                        ValueEventListener lenderCommentListern = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                if (dataSnapshot2.exists()){
                                    for (DataSnapshot ds : dataSnapshot2.getChildren()) {
                                        final RatingAndComment com = ds.getValue(RatingAndComment.class);
                                        final String c_rating = com.getRating();
                                        final String c_userID = com.getID();
                                        final String c_comment = com.getComment();
                                        FirebaseDatabase o = FirebaseDatabase.getInstance();
                                        DatabaseReference userRef = o.getReference("users/" + c_userID);
                                        ValueEventListener photoListern = new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                                                if (dataSnapshot3.exists()){
                                                    final NormalUser n = dataSnapshot3.getValue(NormalUser.class);
                                                    final String c_username = n.getName();
                                                    comment comment = new comment(c_username,"", c_rating, c_comment);
                                                    lenderCommentList.add(comment);
                                                    lenderCommentAdapter.notifyDataSetChanged();
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

                    }else{
                        lenderRateTextView.setText("has not lent any book yet!");
                        lenderSeeMore.setVisibility(GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        r1.addListenerForSingleValueEvent(lenderListern);
        lenderListView.setAdapter(lenderCommentAdapter);

        lenderSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchingUserDetail.this, CommentDetail.class);
                intent.putExtra("id",profileID);
                intent.putExtra("type","owner");
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
                    if (borrowBookTime == 0){
                        borrowBookTimeTextView.setText("Has not borrowed any book yet!");
                        lenderSeeMore.setVisibility(GONE);
                    }else{
                        if(borrowBookTime == 1){
                            lenderSeeMore.setVisibility(GONE);
                        }
                        borrowBookTimeTextView.setText(Integer.toString(borrowBookTime));
                        borrowRateTextView.setText(b.getBorrowerRating());
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

                                        ValueEventListener photoListern = new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                                                if (dataSnapshot3.exists()){
                                                    final NormalUser n = dataSnapshot3.getValue(NormalUser.class);
                                                    final String c_username = n.getName();
                                                    comment comment = new comment(c_username,"", c_rating, c_comment);
                                                    borrowerCommentList.add(comment);
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
                startActivity(intent);
            }
        });





    }
}
