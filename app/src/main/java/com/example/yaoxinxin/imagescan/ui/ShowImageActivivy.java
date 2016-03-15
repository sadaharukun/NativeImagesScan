package com.example.yaoxinxin.imagescan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.yaoxinxin.imagescan.R;
import com.example.yaoxinxin.imagescan.adapter.ImageBeanAdapter;
import com.example.yaoxinxin.imagescan.adapter.ShowImagesAdapter;
import com.example.yaoxinxin.imagescan.support.GalleryAnimationActivity;
import com.example.yaoxinxin.imagescan.widget.defaultItemDecoratoin;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowImageActivivy extends AppCompatActivity {

    private static final String TAG = ShowImageActivivy.class.getSimpleName();

    @Bind(R.id.recycle_images)
    RecyclerView mRecyclerView;

    @Bind(R.id.toolbar)
    Toolbar mToolBar;

    private ShowImagesAdapter mAdapter;

    private List<String> paths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image_activivy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        initView();

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            paths = bundle.getStringArrayList("paths");
            initData();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void initView() {

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new defaultItemDecoratoin(this, LinearLayoutManager.HORIZONTAL));
        mRecyclerView.addItemDecoration(new defaultItemDecoratoin(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolBar.setTitle("ShowImageView");
        mToolBar.setLogo(R.mipmap.ic_launcher);
        mToolBar.setNavigationIcon(R.mipmap.goback_black);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    private void initData() {

        mAdapter = new ShowImagesAdapter(this, paths, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnRecyclerViewItemCliclListener(new ImageBeanAdapter.OnMyRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent intent = new Intent(ShowImageActivivy.this, GalleryAnimationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("paths", (ArrayList<String>)paths);
                bundle.putInt("currentPosition", position);
                intent.putExtras(bundle);
                Log.e(TAG,"clickclick");
                startActivity(intent);

            }
        });
    }


}
