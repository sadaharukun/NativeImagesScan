package com.example.yaoxinxin.imagescan.support;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.yaoxinxin.imagescan.R;
import com.example.yaoxinxin.imagescan.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 展示当前分组所有图片Activity
 */
public class GalleryAnimationActivity extends FragmentActivity {

    private Map<Integer, Fragment> mContainFragments = new HashMap<Integer, Fragment>();

    private List<String> urls = new ArrayList<String>();

    private FragmentAdapter mAdapter;

    private int mCurrentPosition;

    /**
     * 是否是第一次预览图片
     */
    private boolean mFirstPageOn;

    @Bind(R.id.gallery)
    ViewPager mGallery;

    private View mRootView;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_animation);

        ButterKnife.bind(this);

        if (getIntent() != null) {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            urls = bundle.getStringArrayList("paths");
            mCurrentPosition = bundle.getInt("currentPosition");
        }

        mRootView = this.findViewById(android.R.id.content);

        mAdapter = new FragmentAdapter(getSupportFragmentManager(), urls, mCurrentPosition);
        mGallery.setAdapter(mAdapter);
        mGallery.setCurrentItem(mCurrentPosition);
        mGallery.setOffscreenPageLimit(1);
        mGallery.setPageTransformer(true, new ZoomOutPageTransformer());

        mGallery.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 设置背景
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public ObjectAnimator setAnimatorBackground() {

        final ColorDrawable backgroundColor = new ColorDrawable(Color.BLACK);

        ObjectAnimator bgAnim = ObjectAnimator
                .ofInt(backgroundColor, "alpha", 0, 255);
        bgAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                backgroundColor.setAlpha((Integer) animation.getAnimatedValue());
                mRootView.setBackground(backgroundColor);
            }
        });
        return bgAnim;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBackgroundImmediately() {
        if (mRootView.getBackground() == null) {
            mRootView.setBackground(new ColorDrawable(Color.BLACK));
        }
    }

}
