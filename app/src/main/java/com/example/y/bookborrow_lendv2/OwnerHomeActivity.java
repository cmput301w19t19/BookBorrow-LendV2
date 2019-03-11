
package com.example.y.bookborrow_lendv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OwnerHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        TextView myBooks = findViewById(R.id.select_owner_menu_1);
        TextView mySearch = findViewById(R.id.select_owner_menu_2);
        TextView myScan = findViewById(R.id.select_owner_menu_3);
        Button backButton = findViewById(R.id.back_button);

        myBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomeActivity.this,MyBookList.class);
                startActivity(intent);
            }
        });

        mySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomeActivity.this, Search.class);
                startActivity(intent);
            }
        });

        myScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(OwnerHomeActivity.this, Scan.class);
                //startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomeActivity.this, home_page.class);
                startActivity(intent);
            }
        });
    }



}
