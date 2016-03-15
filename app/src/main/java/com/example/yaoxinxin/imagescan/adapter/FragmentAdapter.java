package com.example.yaoxinxin.imagescan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.yaoxinxin.imagescan.support.ContainFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yaoxinxin on 16/3/15.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private Map<Integer, Fragment> mContainFragments = new HashMap<Integer, Fragment>();


    private List<String> mData;

    private int mCurrentPosition;

    public FragmentAdapter(FragmentManager fm, List<String> mData, int curPos) {
        super(fm);
        this.mData = mData;
        this.mCurrentPosition = curPos;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mContainFragments.get(position);
        if (fragment == null) {
            fragment = ContainFragment.newInstance(mData.get(position), position + "");
            mContainFragments.put(position, fragment);
        }
        return fragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return super.isViewFromObject(view, object);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }
}
