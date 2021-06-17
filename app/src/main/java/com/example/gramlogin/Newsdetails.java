package com.example.gramlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class Newsdetails extends AppCompatActivity {

    private TextView heading, detail;
    private EditText addComment;
    private Button submitComment;
    private DatabaseReference usereRef, commentRef;
    private String postkey;
    Comment_recyclerAdapter commentRecyclerAdapter;
    RecyclerView commentRecview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetails);

        heading = (TextView) findViewById(R.id.id_headingNews);
        detail = (TextView) findViewById(R.id.id_detailedNews);
        addComment = (EditText) findViewById(R.id.id_addComment);
        submitComment = (Button) findViewById(R.id.id_submitComment);
        postkey = getIntent().getStringExtra("postkey");
        usereRef = FirebaseDatabase.getInstance().getReference().child("users");
        commentRef = FirebaseDatabase.getInstance().getReference().child("news").child(postkey).child("comments");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();

        commentRecview = (RecyclerView) findViewById(R.id.id_commentRecview);
        commentRecview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        heading.setText(getIntent().getStringExtra("heading"));
        detail.setText(getIntent().getStringExtra("detail"));

        FirebaseRecyclerOptions<Comment_model> options =
                new FirebaseRecyclerOptions.Builder<Comment_model>()
                        .setQuery(commentRef, Comment_model.class)
                        .build();

        commentRecyclerAdapter = new Comment_recyclerAdapter(options);
        commentRecview.setAdapter(commentRecyclerAdapter);

        submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usereRef.child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String username = snapshot.child("name").getValue().toString();
                            processcomment(username, userId);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    private void processcomment(String username, String userId){
        String commentpost = addComment.getText().toString();
        String randomkey = userId+""+new Random().nextInt(1000);

        Calendar datevalue = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yy");
        String cdate = dateFormat.format(datevalue.getTime());

        SimpleDateFormat timeForm = new SimpleDateFormat("HH:mm");
        String ctime = timeForm.format(datevalue.getTime());

        HashMap cmnt = new HashMap();
        cmnt.put("uid", userId);
        cmnt.put("name", username);
        cmnt.put("date", cdate);
        cmnt.put("time", ctime);
        cmnt.put("usermsg", commentpost);

        commentRef.child(randomkey).updateChildren(cmnt)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            addComment.setText("");
                            Toast.makeText(getApplicationContext(), "Comment posted thank you.. ", Toast.LENGTH_SHORT).show();
                        }else{
                            addComment.setText("");
                            Toast.makeText(getApplicationContext(), "Sorry, Try again....", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        commentRecyclerAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        commentRecyclerAdapter.stopListening();
    }
}