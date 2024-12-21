package com.thick124.lop124LTTD03.nhomTPB;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AboutFragment();
            case 1:
                return new AuthorFragment();
            default:
                return new AboutFragment();
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
}

