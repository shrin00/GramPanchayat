package com.example.gramlogin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
    //tabcount variable
    int tabCount;
    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabCount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //Code to switch between fragments when respective tab selected
        switch (position){
            case 0: return new Services();
            case 1: return new Schemes();
            case 2: return new News();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
