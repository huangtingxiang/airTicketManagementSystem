package com.jdxiang.airTicket.ui.home;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeSectionPagesAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>();

    private final List<String> titles = new ArrayList<>();

    public HomeSectionPagesAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        this.fragments.add(fragment);
        this.titles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
            return fragments.get(position);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public String getPageTitle(int position) {
        return titles.get(position);
    }
}
