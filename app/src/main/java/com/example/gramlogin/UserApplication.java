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

    private RecyclerView userapplicationrecview, approvedrecview, rejectedrecview;
    UserApplicationAdapter useradapter;
    private FirebaseUser user;
    private DatabaseReference applicationref, userapplicationsref, approvedref, rejectref;
    private ArrayList<UserApplicationModel> applications = new ArrayList<>();
    private ArrayList<UserApplicationModel> approved = new ArrayList<>();
    private ArrayList<UserApplicationModel> rejected = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_application);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        applicationref = FirebaseDatabase.getInstance().getReference().child("Services");

        userapplicationrecview = (RecyclerView) findViewById(R.id.userapplication_recview);
        userapplicationrecview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        approvedrecview = (RecyclerView) findViewById(R.id.application_approved_recview);
        approvedrecview.setLayoutManager(new LinearLayoutManager((getApplicationContext())));
        rejectedrecview = (RecyclerView) findViewById(R.id.application_rejected_recview);
        rejectedrecview.setLayoutManager(new LinearLayoutManager((getApplicationContext())));


        applicationref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap: snapshot.getChildren()){
                    String servicename = snap.child("servicename").getValue().toString();
                    Log.d("hello", servicename);

                    userapplicationsref = applicationref.child(snap.getKey().toString()).child("Applications").child(userId);
                    userApplicationView(userapplicationsref, servicename, 0);

                    approvedref = applicationref.child(snap.getKey().toString()).child("Approved").child(userId);
                    userApplicationView(approvedref, servicename, 1);

                    rejectref = applicationref.child(snap.getKey().toString()).child("Rejected").child(userId);
                    userApplicationView(rejectref, servicename, 2);

//                    userapplicationsref.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
//                            if (snapshot1.exists()){
//                                String username, dat;
//                                username = snapshot1.child("name").getValue().toString();
//                                dat = snapshot1.child("date").getValue().toString()+" "+snapshot1.child("time").getValue().toString();
//                                applications.add(new UserApplicationModel(servicename, username, dat));
//                                for (UserApplicationModel i : applications){
//                                    Log.d("hello", i.getServicename()+" "+i.getUsername()+" "+ i.getDat());
//                                }
//                                useradapter = new UserApplicationAdapter(applications, getApplicationContext());
//                                userapplicationrecview.setAdapter(useradapter);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
                }

                System.out.println("applications"+applications.size());
                useradapter = new UserApplicationAdapter(applications, getApplicationContext());
                userapplicationrecview.setAdapter(useradapter);
                System.out.println("approved"+approved.size());
                approvedrecview.setAdapter(new UserApplicationAdapter(approved, getApplicationContext()));
                System.out.println("rejected"+rejected.size());
                rejectedrecview.setAdapter(new UserApplicationAdapter(rejected, getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }


    public void userApplicationView(DatabaseReference ref, String servicename, int id){

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                if (snapshot1.exists()){
                    String username, dat;
                    username = snapshot1.child("name").getValue().toString();
                    dat = snapshot1.child("date").getValue().toString()+" "+snapshot1.child("time").getValue().toString();

                    if (id == 0){
                        applications.add(new UserApplicationModel(servicename, username, dat));
                    }
                    if (id == 1){approved.add(new UserApplicationModel(servicename, username, dat));}
                    if(id == 2){rejected.add(new UserApplicationModel(servicename, username, dat));}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}