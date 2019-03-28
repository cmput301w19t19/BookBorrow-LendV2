package com.example.y.bookborrow_lendv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SearchingUserDetail extends AppCompatActivity {
    TextView userName;
    TextView phoneNumber;
    TextView lendBookTime;
    TextView borrowBookTime;
    TextView ownerRate;
    TextView BorrowRate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_user_detail);

        userName = (TextView)findViewById(R.id.searchUserDetailUserName);
        phoneNumber = (TextView)findViewById(R.id.searchUserDetailPhoneInput);
        lendBookTime = (TextView)findViewById(R.id.searchUserDetaillendBookTimeINput);
        borrowBookTime = (TextView)findViewById(R.id.searchUserDetailBorrowerBookTimeInput);
        ownerRate = (TextView)findViewById(R.id.searchUserDetaiBorrowerRating);
        BorrowRate = (TextView)findViewById(R.id.searchUserDetailOwnerRating);


    }
}
