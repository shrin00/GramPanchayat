package com.example.gramlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ServiceForm extends AppCompatActivity {
    private LinearLayout serviceform;
    private String postkey;
    private DatabaseReference requiredref, applicationref, userprofileref;
    private TextView hello;
    private Button submitform;
    private ProgressBar pb;
    ArrayList<String> fields = new ArrayList<>();
    List<EditText> ed = new ArrayList<EditText>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_form);
        serviceform = (LinearLayout) findViewById(R.id.id_serviceformlayout);
        hello = (TextView) findViewById(R.id.hello_world);
        submitform = (Button) findViewById(R.id.id_servirform_submit);
        pb = (ProgressBar) findViewById(R.id.service_progressBar);
        postkey = getIntent().getStringExtra("postkey");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        hello.setText(getIntent().getStringExtra("servicename"));

        requiredref = FirebaseDatabase.getInstance().getReference().child("Services").child(postkey).child("required");
        requiredref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    fields.add(snap.getValue().toString());
                }
                addfields(fields);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        HashMap ser = new HashMap();
        userprofileref = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        userprofileref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot snap : snapshot.getChildren()){
                   ser.put(snap.getKey().toString(), snap.getValue().toString());
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        applicationref = FirebaseDatabase.getInstance().getReference().child("Services").child(postkey).child("Applications");
        submitform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                Calendar datevalue = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yy");
                String cdate = dateFormat.format(datevalue.getTime());

                SimpleDateFormat timeForm = new SimpleDateFormat("HH:mm");
                String ctime = timeForm.format(datevalue.getTime());

                for (EditText i:ed){
                    ser.put(i.getHint().toString(), i.getText().toString());
                }
                ser.put("email", user.getEmail().toString());
                ser.put("date", cdate);
                ser.put("time", ctime);

                applicationref.child(userId).updateChildren(ser)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                pb.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "Form submitted successfully...", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        });
            }
        });

    }

    public void addfields(ArrayList<String> fields){
        for (String i:fields){
            EditText newview = new EditText(getApplicationContext());
            ed.add(newview);
            LinearLayout.LayoutParams params = (new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            params.setMargins(0, 10, 0, 10);
            newview.setLayoutParams(params);

            newview.setHint(i);
            newview.setBackgroundColor(getResources().getColor(R.color.Lightyellow));
            newview.setElevation(10);
            serviceform.addView(newview);
        }
    }

}