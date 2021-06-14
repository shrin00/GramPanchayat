package com.example.gramlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginpage1 extends AppCompatActivity {

    private TextInputLayout tEmail, tPassword;
    private  Button tLogin;
    private  TextView mCreateBtn;
    private  ProgressBar pb;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference userReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage1);
        mAuth = FirebaseAuth.getInstance();

        tEmail = (TextInputLayout) findViewById(R.id.id_emailLogin);
        tPassword = (TextInputLayout) findViewById(R.id.id_pwdLogin);
        tLogin = (Button) findViewById(R.id.id_login);
        mCreateBtn=findViewById(R.id.id_registerHere);
        pb = (ProgressBar) findViewById(R.id.progressBar3);

        tLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tEmail.getEditText().getText().toString().trim();
                String password = tPassword.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                 }else if(TextUtils.isEmpty(password)){
                    tPassword.setError("Password is required");
                }else if(password.length()<=6){
                    tEmail.setError("Email is required");
                    tPassword.setError("Password must have more than 6 characters");
                }else {
                    pb.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(loginpage1.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        pb.setVisibility(View.INVISIBLE);
//                                       checkUserType(mAuth.getCurrentUser().getUid());
                                        startActivity(new Intent(loginpage1.this, MainActivity.class));
                                        finish();
                                    }else{
                                        pb.setVisibility(View.INVISIBLE);
                                        tEmail.getEditText().setText("");
                                        tPassword.getEditText().setText("");
                                        Toast.makeText(loginpage1.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),register.class));
            }
        });
    }

//    public void checkUserType(String uid) {
//        db = FirebaseDatabase.getInstance();
//        userReference = db.getReference().child("users").child(uid);
//        userReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    if (snapshot.child("admin").exists()){
//                        boolean isAdmin = (boolean) snapshot.child("admin").getValue();
//                        if (isAdmin){
//                            startActivity(new Intent(loginpage1.this, Pdo_main.class));
//                            finish();
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

}