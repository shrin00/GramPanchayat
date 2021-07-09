package com.example.gramlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
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

public class Paytax extends AppCompatActivity {

    private Button paytax;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference userprofileref, transactions;
    String usercontactno, cdate, ctime;
    private RecyclerView transactionrecview;
    private TransactionAdapter transadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytax);
        Calendar datevalue = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yy");
        cdate = dateFormat.format(datevalue.getTime());

        SimpleDateFormat timeForm = new SimpleDateFormat("HH:mm");
        ctime = timeForm.format(datevalue.getTime());

        transactionrecview = findViewById(R.id.transaction_recview);
        transactionrecview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //reading data from firebasedatabase
        FirebaseRecyclerOptions<TransactionData> options =
                new FirebaseRecyclerOptions.Builder<TransactionData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Transactions").child(user.getUid().toString()), TransactionData.class)
                        .build();
        transadapter = new TransactionAdapter(options);
        transactionrecview.setAdapter(transadapter);


        transactions = FirebaseDatabase.getInstance().getReference().child("Transactions");
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


                HashMap trans = new HashMap();
                trans.put("date", cdate);
                trans.put("time", ctime);
                trans.put("email", user.getEmail().toString());
                trans.put("amount", data.getStringExtra("fees").toString());
                trans.put("result", "1");

                transactions.child(user.getUid().toString()).child(data.getStringExtra("key")).setValue(trans);

                Toast.makeText(getApplicationContext(), "Tax payment is successfull..."+data.getStringExtra("key"), Toast.LENGTH_SHORT).show();
            }
            if (resultCode == RESULT_CANCELED){

                Toast.makeText(getApplicationContext(), "Tax payment is not successfull...", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        transadapter.startListening();


    }

    @Override
    public void onStop() {
        super.onStop();
        transadapter.stopListening();
    }
}