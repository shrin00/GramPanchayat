package com.example.gramlogin;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private TabItem  tab_services, tab_schemes, tab_news;
    private ViewPager viewPager;
    private PageAdapter pageAdapter;
    private Toolbar toolbar;


    //firebase authentication instance
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tablayout1);
        tab_services = (TabItem) findViewById(R.id.tabitem_services);
        tab_schemes = (TabItem) findViewById(R.id.tabitem_schemes);
        tab_news = (TabItem) findViewById(R.id.tabitem_news);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

        //firebase instance
        mFirebaseAuth = FirebaseAuth.getInstance();

        //pageadapter class instence
        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        //tabselected listener
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //sets data on view pager from selected position fragments
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2){
                    pageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //listener for slide screen
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_account:
                Intent acc = new Intent(MainActivity.this, Account.class);
                acc.putExtra("email", mFirebaseAuth.getCurrentUser().getEmail());
                acc.putExtra("UID", mFirebaseAuth.getCurrentUser().getUid());
                startActivity(acc);

                Toast.makeText(this, "Account is selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item_complaint:
                Toast.makeText(MainActivity.this, "Complaint is clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item_pdo:
                Intent pdo_activity = new Intent(this, Pdo_main.class);
                startActivity(pdo_activity);
                Toast.makeText(MainActivity.this, "PDO is clicked", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }else {
            startActivity(new Intent(this, loginpage1.class));
        }
    }
}