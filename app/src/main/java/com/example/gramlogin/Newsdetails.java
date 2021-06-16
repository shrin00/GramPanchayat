package com.example.gramlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Newsdetails extends AppCompatActivity {

    TextView heading, detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetails);

        heading = (TextView) findViewById(R.id.id_headingNews);
        detail = (TextView) findViewById(R.id.id_detailedNews);

        heading.setText(getIntent().getStringExtra("heading"));
        detail.setText(getIntent().getStringExtra("detail"));

    }
}