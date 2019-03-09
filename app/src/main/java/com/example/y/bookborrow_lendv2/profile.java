package com.example.y.bookborrow_lendv2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class profile extends AppCompatActivity {
    private Button updateButton;
    private EditText inputEmail, inputUserName, inputPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        //get the current logged in usesr ID
        String uid = NormalUser.Instance().getUid();

        updateButton = (Button) findViewById(R.id.UpdateButton);
        inputEmail = (EditText) findViewById(R.id.InputEmail);
        // inputUserName = (EditText) findViewById(R.id.InputUserName);
        inputPhone = (EditText) findViewById(R.id.InputPhone);



        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String userName = inputUserName.getText().toString();
                String phone = inputPhone.getText().toString();



            }});


    }

}



