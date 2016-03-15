package com.example.yaoxinxin.imagescan.ui;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.yaoxinxin.imagescan.bean.ImageBean;
import com.example.yaoxinxin.imagescan.adapter.ImageBeanAdapter;
import com.example.yaoxinxin.imagescan.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int RET_OK = 0x01;

    private RecyclerView mRecyclerView;

    private List<ImageBean> mImageBeans = new ArrayList<ImageBean>();

    private Map<String, List<String>> mGroupImages = new HashMap<String, List<String>>();

    private ImageBeanAdapter mAdapter;

    private ProgressDialog mDialog;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mDialog.dismiss();
            if (msg.what == RET_OK) {
                mImageBeans = getImageList();

                mAdapter = new ImageBeanAdapter(MainActivity.this
                        , mImageBeans, mRecyclerView);

                mRecyclerView.setAdapter(mAdapter);

                mAdapter.setOnMyRecycleViewItemClickListener(new ImageBeanAdapter.OnMyRecycleViewItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        Log.e(TAG, "position=" + position);
                        Intent intent = new Intent(MainActivity.this, ShowImageActivivy.class);
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("paths", (ArrayList<String>) mGroupImages.
                                get(mImageBeans.get(position).getFolderName()));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

        initData();

    }

    private void initData() {

        getImages();

    }


    /**
     * 将图片文件封装为List集合
     *
     * @return
     */
    private List<ImageBean> getImageList() {


        if (mGroupImages == null) {
            return null;
        }

        Set<Map.Entry<String, List<String>>> entrySet = mGroupImages.entrySet();

        for (Map.Entry<String, List<String>> entry : entrySet) {

            String dirName = entry.getKey();
            List<String> images = entry.getValue();

            ImageBean bean = new ImageBean();
            bean.setTopImagePath(images.get(0));
            bean.setCount(images.size());
            bean.setFolderName(dirName);

            mImageBeans.add(bean);

        }


        return mImageBeans;
    }

    /**
     * 加载手机中的图片
     */
    private void getImages() {
        mDialog.show();
        new Thread() {

            @Override
            public void run() {
                super.run();
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                ContentResolver mContentResolver = MainActivity.this.getContentResolver();

                //只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);


                if (mCursor == null)

                {
                    return;
                }

                while (mCursor.moveToNext())

                {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    //图片父路径
                    String dirName = new File(path).getParentFile().getName();

                    if (mGroupImages.get(dirName) == null) {
                        List<String> urls = new ArrayList<String>();
                        urls.add(path);
                        mGroupImages.put(dirName, urls);
                    } else {
                        mGroupImages.get(dirName).add(path);
                    }
                }

                Message msg = mHandler.obtainMessage();
                msg.what = RET_OK;
                msg.obj = mGroupImages;
                mHandler.sendMessage(msg);

            }
        }.start();


    }

    private void initView() {
        ActionBar bar = getActionBar();
        mDialog = ProgressDialog.show(this,"","加载中...");
//        bar.setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) this.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        mRecyclerView.addItemDecoration(new div);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                finish();
                return true;


            case R.id.action_settings:

                break;
        }


        return true;
    }
}
