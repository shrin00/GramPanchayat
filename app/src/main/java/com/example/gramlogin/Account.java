package com.example.gramlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {
    private TextView userEmail, userName, userSex, userContactNo, userDob;
    private Button logout;
    private String userId;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mUserReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        userEmail = (TextView) findViewById(R.id.id_profileEmail);
        userName = (TextView) findViewById(R.id.id_profileName);
        userSex = (TextView) findViewById(R.id.id_profileSex);
        userContactNo = (TextView) findViewById(R.id.id_profileContactNo);
        userDob = (TextView) findViewById(R.id.id_profileDob);
        logout = (Button) findViewById(R.id.id_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Account.this, loginpage1.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        mUserReference = mDatabase.getReference().child("users").child(userId);
        mUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    userEmail.setText(user.getEmail().toString());
                    userName.setText(snapshot.child("name").getValue().toString());
                    userContactNo.setText(snapshot.child("contactNo").getValue().toString());
                    userSex.setText(snapshot.child("sex").getValue().toString());
                    userDob.setText(snapshot.child("dob").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}