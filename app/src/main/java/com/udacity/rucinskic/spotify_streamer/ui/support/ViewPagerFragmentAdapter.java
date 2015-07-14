package com.udacity.rucinskic.spotify_streamer.ui.support;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.udacity.rucinskic.spotify_streamer.App;
import com.udacity.rucinskic.spotify_streamer.interfaces.Downloadable;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>(App.getNumberOfApiCalls());
    private final List<String> tabTitles = new ArrayList<>(App.getNumberOfApiCalls());

    public ViewPagerFragmentAdapter(final FragmentManager manager) { super(manager); }

    public void addFragment(final Fragment fragment, final Downloadable tab) {

        fragments.add(fragment);
        tabTitles.add(tab.toString());
        this.notifyDataSetChanged();

    }

    public void clearTabs() {

        fragments.clear();
        tabTitles.clear();
        this.notifyDataSetChanged();

    }

    @Override
    public Fragment getItem(final int position) { return fragments.get(position); }

    @Override
    public int getCount() { return fragments.size(); }

    @Override
    public CharSequence getPageTitle(final int position) { return tabTitles.get(position); }


}
