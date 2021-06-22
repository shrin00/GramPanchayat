package com.example.gramlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserApplication extends AppCompatActivity {

    private RecyclerView userapplicationrecview;
    UserApplicationAdapter useradapter;
    private FirebaseUser user;
    private DatabaseReference applicationref, userapplicationsref;
    private ArrayList<UserApplicationModel> applications = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_application);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        applicationref = FirebaseDatabase.getInstance().getReference().child("Services");

        userapplicationrecview = (RecyclerView) findViewById(R.id.userapplication_recview);
        userapplicationrecview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        applicationref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap: snapshot.getChildren()){
                    String servicename = snap.child("servicename").getValue().toString();

                    userapplicationsref = applicationref.child(snap.getKey().toString()).child("Applications").child(userId);
                    userapplicationsref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
                            if (snapshot1.exists()){
                                String username, dat;
                                username = snapshot1.child("name").getValue().toString();
                                dat = snapshot1.child("date").getValue().toString()+" "+snapshot1.child("time").getValue().toString();
                                applications.add(new UserApplicationModel(servicename, username, dat));
                                for (UserApplicationModel i : applications){
                                    Log.d("hello", i.getServicename()+" "+i.getUsername()+" "+ i.getDat());
                                }
                                useradapter = new UserApplicationAdapter(applications, getApplicationContext());
                                userapplicationrecview.setAdapter(useradapter);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
    }

}