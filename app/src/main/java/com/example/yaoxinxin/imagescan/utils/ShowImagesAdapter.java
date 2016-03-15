package com.example.yaoxinxin.imagescan.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yaoxinxin.imagescan.R;
import com.example.yaoxinxin.imagescan.adapter.NativeImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yaoxinxin on 16/3/14.
 */
public class ShowImagesAdapter extends RecyclerView.Adapter<ShowImagesAdapter.MyViewHolder> {


    private Context mC;

    private List<String> mData;

    private LayoutInflater mInflater;

    private RecyclerView mRootView;

    public ShowImagesAdapter(Context c, List<String> data, RecyclerView view) {
        this.mC = c;
        this.mData = data;
        this.mRootView = view;
        this.mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.item_show_image, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final String path = mData.get(position);
        holder.mImageView.setTag(path);

        Bitmap bitmap = NativeImageLoader.getInstance().loadImage(path, null, new NativeImageLoader.ImageCallBack() {
            @Override
            public void callback(String url, Bitmap bitmap) {
                ImageView imageView = (ImageView) mRootView.findViewWithTag(path);

                if (url.equals(holder.mImageView.getTag())) {
                    imageView.setImageBitmap(bitmap);
//                    holder.mImageView.setImageBitmap(bitmap);
                } else {
                    imageView.setImageResource(R.mipmap.ic_launcher);
//                    holder.mImageView.setImageResource(R.mipmap.ic_launcher);
                }
            }
        });

        if (bitmap == null) {
            holder.mImageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            holder.mImageView.setImageBitmap(bitmap);
        }
//        holder.mImageView.setImageResource(R.mipmap.ic_launcher);
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image)
        ImageView mImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
