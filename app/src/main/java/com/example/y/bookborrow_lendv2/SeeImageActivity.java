package com.example.y.bookborrow_lendv2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SeeImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_image);
        ImageView BigPhoto = findViewById(R.id.BigImage);
        Intent intent = getIntent();
        byte[] byte1 = intent.getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byte1,0,byte1.length);
        BigPhoto.setImageBitmap(bitmap);
    }
}
