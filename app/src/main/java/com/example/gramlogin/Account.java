package com.example.gramlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Account extends AppCompatActivity {
    private TextView userEmail, userId;
    private Button logout;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mAuth = FirebaseAuth.getInstance();

        userEmail = (TextView) findViewById(R.id.id_userEmail);
        userId = (TextView) findViewById(R.id.id_userId);
        logout = (Button) findViewById(R.id.id_logout);

        userEmail.setText(getIntent().getStringExtra("email").toString());
        userId.setText(getIntent().getStringExtra("UID").toString());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Account.this, loginpage1.class));
            }
        });
    }
}