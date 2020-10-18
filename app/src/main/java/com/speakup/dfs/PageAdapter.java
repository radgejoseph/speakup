package com.speakup.dfs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private int numOftabs;

    public PageAdapter(@NonNull FragmentManager fm, int numOftabs) {
        super(fm);
        this.numOftabs = numOftabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ComplaintFragment();
            case 1:
                return new CommendationFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numOftabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
