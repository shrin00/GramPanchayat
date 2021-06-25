package com.example.gramlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Paytax extends AppCompatActivity {

    private Button paytax;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference userprofileref;
    String usercontactno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytax);
        userprofileref = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid().toString());
        userprofileref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usercontactno = snapshot.child("contactNo").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        paytax = (Button) findViewById(R.id.id_taxpay);
        paytax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pyintent = new Intent(getApplicationContext(), Payments.class);
                pyintent.putExtra("email", user.getEmail().toString());
                pyintent.putExtra("contactno", usercontactno);
                pyintent.putExtra("fees", "100");
                startActivityForResult(pyintent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                Toast.makeText(getApplicationContext(), "Tax payment is successfull...", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "Tax payment is not successfull...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}