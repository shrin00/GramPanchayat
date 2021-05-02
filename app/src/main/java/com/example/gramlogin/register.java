package com.example.gramlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {
    EditText FullName, Email,Password,Phone,Adhaar,Address;
    Button mregisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FullName=findViewById(R.id.editTextTextPersonName);
        Email=findViewById(R.id.editTextTextEmailAddress2);
        Password=findViewById(R.id.editTextTextPassword2);
        Phone=findViewById(R.id.editTextNumber2);
        Adhaar=findViewById(R.id.editTextNumber);
        Address=findViewById(R.id.editTextTextPersonName2);
        mregisterBtn=findViewById(R.id.button2);
        fAuth= FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
        mLoginBtn=findViewById(R.id.textView7);

      /* if(fAuth.getCurrentUser()!= null){
           startActivity(new Intent(getApplicationContext(),MainActivity.class));
          finish();
      }*/
        mregisterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email=Email.getText().toString().trim();
                String password=Password.getText().toString().trim();
                //String adhaar=Adhaar.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Email.setError("Email is required");
                }
                if(TextUtils.isEmpty(password)){
                    Password.setError("Password is required");
                }
                /*if(TextUtils.isEmpty(adhaar)){
                    Adhaar.setError("Adhaar number is required");
                }*/
                if(password.length()<=6){
                    Password.setError("Password must have more than 6 characters");
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(register.this, "User Registered", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else{
                            Toast.makeText(register.this, "Error! Try again"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
                }

            }


        );
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),loginpage1.class));
            }
        });

        }

        }
//https://sw.blackmagicdesign.com/DaVinciResolve/v17.1.1/DaVinci_Resolve_17.1.1_Windows.zip?Key-Pair-Id=APKAJTKA3ZJMJRQITVEA&Signature=K/ufPKY4TwYbJRgiY9k8WQzWv/J9QzP8f+CgeS6txpUlP8/K8sqaJoeFbsXLL8FCTL04qSUYpa1Nzf4Ejuno11JPXW9lHT2rjnxJScxU5xCh8DY/dR4Nu7W/zYXG7YxmHfZioBXT5n5ZnsHL25i7TR5Td11y2gcyMXhuxZ02eVa0S5eDcEXQx+SZ1hJ6IUtZIXSaqtAQy/GRxgUlumZZYLHmiJghmOoWCFx/raNgKRFhu6hwHEnhK4zq9giz2nnEni3PrPchcDBaQWaFz2+6dUcUOWqw38N2kNTO2UYg+1pz7j7UCAh2QqawG47zdGPaF4g5P8FjRltduqOHy8yHTw==&Expires=1619628739