package com.example.gramlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.BatchUpdateException;

public class register extends AppCompatActivity {

    private TextInputLayout tEmail, tPassword, tConfirmPassword, tName, tContact, tDob, tSex;
    private ProgressBar pb;
    private Button register;
    private TextView alSignIn;
    private String userId;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference userProfileReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        //database
        database = FirebaseDatabase.getInstance();
        userProfileReference = database.getReference().child("users");

        tEmail = (TextInputLayout) findViewById(R.id.id_emailRegister);
        tPassword = (TextInputLayout) findViewById(R.id.id_pwdRegister);
        tConfirmPassword = (TextInputLayout) findViewById(R.id.id_confirmpwdRegister);
        tName = (TextInputLayout) findViewById(R.id.id_userName);
        tContact = (TextInputLayout) findViewById(R.id.id_contactNo);
        tDob = (TextInputLayout) findViewById(R.id.id_dob);
        tSex = (TextInputLayout) findViewById(R.id.id_sex);
        pb = (ProgressBar) findViewById(R.id.progressBar2);
        register = (Button) findViewById(R.id.id_register);
        alSignIn = (TextView) findViewById(R.id.id_signInRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = tEmail.getEditText().getText().toString().trim();
                String password = tPassword.getEditText().getText().toString().trim();
                String cPwd = tConfirmPassword.getEditText().getText().toString().trim();


                if(TextUtils.isEmpty(email)){
                    tEmail.setError("Email is required");
                }else if(TextUtils.isEmpty(password)){
                    tPassword.setError("Password is required");
                }else if(password.length()<=6){
                    tPassword.setError("Password must have more than 6 characters");
                }else if (TextUtils.isEmpty(cPwd)){
                    tConfirmPassword.setError("Please Confirm password!");
                }else{
                    pb.setVisibility(View.VISIBLE);
                    if (password.equals(cPwd)){
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            pb.setVisibility(View.INVISIBLE);

                                            userId = mAuth.getCurrentUser().getUid();
                                            writeNewUser(userId);

                                            tEmail.getEditText().setText("");
                                            tPassword.getEditText().setText("");
                                            tConfirmPassword.getEditText().setText("");
                                            Toast.makeText(register.this, "Register successful, Please Login", Toast.LENGTH_SHORT).show();
                                            mAuth.signOut();
                                            startActivity(new Intent(register.this, loginpage1.class));
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            pb.setVisibility(View.INVISIBLE);
                                            tEmail.getEditText().setText("");
                                            tPassword.getEditText().setText("");
                                            tConfirmPassword.getEditText().setText("");
                                            Toast.makeText(register.this, "Register Failed, Please try again", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });

                    }
                    else{
                        pb.setVisibility(View.INVISIBLE);
                        tEmail.getEditText().setText("");
                        tPassword.getEditText().setText("");
                        tConfirmPassword.getEditText().setText("");
                        tPassword.setError("Password dose not match!");
                        tConfirmPassword.setError("Password dose not match!");
                    }
                }
            }
        });

        alSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this, loginpage1.class));
            }
        });

    }

    public void writeNewUser(String userId){
        String name = tName.getEditText().getText().toString();
        String contactNo = tContact.getEditText().getText().toString();
        String dob = tDob.getEditText().getText().toString();
        String sex = tSex.getEditText().getText().toString();

        UserProfile user = new UserProfile(name, contactNo, dob, sex, "user");
        userProfileReference.child(userId).setValue(user);
    }
}