package com.example.gramlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ServiceForm extends AppCompatActivity {
    private LinearLayout serviceform;
    private String postkey, fees, usercontactno, NAME;
    private DatabaseReference requiredref, applicationref, userprofileref, fileref;
    private TextView hello;
    private Button submitform, pointerButton;
    private ProgressBar pb;
    Uri filepath;
    ArrayList<String> fields = new ArrayList<>();
    ArrayList<String> files = new ArrayList<>();
    List<EditText> ed = new ArrayList<EditText>();
    List<Button> bt = new ArrayList<Button>();
    HashMap ser = new HashMap();
    HashMap fi = new HashMap();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_form);

        serviceform = (LinearLayout) findViewById(R.id.id_serviceformlayout);
        hello = (TextView) findViewById(R.id.hello_world);
        submitform = (Button) findViewById(R.id.id_servirform_submit);
        pb = (ProgressBar) findViewById(R.id.service_progressBar);
        postkey = getIntent().getStringExtra("postkey");
        fees = getIntent().getStringExtra("fees");

        hello.setText(getIntent().getStringExtra("servicename"));

        requiredref = FirebaseDatabase.getInstance().getReference().child("Services").child(postkey).child("required");
        fileref = FirebaseDatabase.getInstance().getReference().child("Services").child(postkey).child("files");
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

        fileref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    files.add(snap.getValue().toString());
                }
                addFiles(files);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userprofileref = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        userprofileref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usercontactno = snapshot.child("contactNo").getValue().toString();
               for (DataSnapshot snap : snapshot.getChildren()){
                   ser.put(snap.getKey().toString(), snap.getValue().toString());
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        for (Button b: bt){
//            b.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Dexter.withContext(getApplicationContext())
//                            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                            .withListener(new PermissionListener() {
//                                @Override
//                                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                                    Intent intent = new Intent();
//                                    intent.putExtra("name", b.getText().toString());
//                                    intent.setType("application/pdf");
//                                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                                    startActivityForResult(Intent.createChooser(intent, "select files"), 101);
//                                }
//
//                                @Override
//                                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
//                                }
//
//                                @Override
//                                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                                    permissionToken.continuePermissionRequest();
//                                }
//                            }).check();
//                }
//            });
//        }


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

                Intent pyintent = new Intent(getApplicationContext(), Payments.class);
                pyintent.putExtra("email", user.getEmail().toString());
                pyintent.putExtra("contactno", usercontactno);
                pyintent.putExtra("fees", fees);
                startActivityForResult(pyintent, 1);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("TAG", "onActivityResult: " + data.getStringExtra("name"));

        if (requestCode == 101 && resultCode == RESULT_OK){
                filepath = data.getData();
//                System.out.println("Hello world"+NAME);
//                System.out.println("Hello "+data);
                uploadprocess(filepath, NAME);
                pointerButton.setVisibility(View.INVISIBLE);
        }

        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                applicationref.child(userId).updateChildren(ser)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                applicationref.child(userId).child("Files").updateChildren(fi)
                                .addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        pb.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(), "Payment successfull and Form submitted successfully...", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        finish();
                                    }
                                });
                            }
                        });
            }

            if (resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "form is not submitted try again...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }

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

    public  void  addFiles(ArrayList<String> file){
        for (String i: file){
            Button newview = new Button(getApplicationContext());
            bt.add(newview);
            LinearLayout.LayoutParams params = (new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            params.setMargins(0, 10, 0, 10);
            newview.setLayoutParams(params);

            newview.setHint(i);
            newview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dexter.withContext(getApplicationContext())
                            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                    NAME = i;
                                    pointerButton = newview;
                                    Intent intent = new Intent();
                                    intent.setType("application/pdf");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(Intent.createChooser(intent, "select files"), 101);
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                    permissionToken.continuePermissionRequest();
                                }
                            }).check();
                }
            });

            newview.setText("upload "+i);
            newview.setBackgroundColor(getResources().getColor(R.color.Lightyellow));
            newview.setElevation(10);
            serviceform.addView(newview);

        }
    }


    public void  uploadprocess(Uri filepath, String name){
        ProgressDialog  pb = new ProgressDialog(this);
        pb.setTitle("File is uploading......!");
        pb.show();

        StorageReference reference = FirebaseStorage.getInstance().getReference().child("uploads/"+System.currentTimeMillis()+".pdf");
        reference.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                fi.put(name, uri.toString());
                                pb.dismiss();
                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float per = (100 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        pb.setMessage("Uploaded..."+(int) per+"%");
                    }
                });
    }

}