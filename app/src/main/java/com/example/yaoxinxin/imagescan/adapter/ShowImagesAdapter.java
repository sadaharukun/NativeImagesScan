package com.example.yaoxinxin.imagescan.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.example.yaoxinxin.imagescan.R;
import com.example.yaoxinxin.imagescan.utils.NativeImageLoader;

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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String path = mData.get(position);
        holder.mImageView.setTag(path);

        //选中点击
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                useAnimation(buttonView, isChecked);
            }
        });

        //图片点击
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position);
                }
            }
        });

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
    }

    /**
     *  选中动画
     *
     * @param buttonView
     * @param isChecked
     */
    private void useAnimation(CompoundButton buttonView, boolean isChecked) {

        float values[] = new float[]{1.0f, 1.1f, 1.2f, 1.5f, 1.3f, 1.2f, 1.1f, 1.0f};
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(buttonView, "scaleX", values), ObjectAnimator.ofFloat(
                buttonView, "scaleY", values
        ));

        set.start();
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image)
        ImageView mImageView;

        @Bind(R.id.checkbox)
        CheckBox mCheckBox;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private ImageBeanAdapter.OnMyRecycleViewItemClickListener mListener;

    public void setOnRecyclerViewItemCliclListener(ImageBeanAdapter.OnMyRecycleViewItemClickListener listener) {
        this.mListener = listener;
    }
}
