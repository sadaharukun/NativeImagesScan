package com.example.yaoxinxin.imagescan.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yaoxinxin.imagescan.R;
import com.example.yaoxinxin.imagescan.bean.ImageBean;
import com.example.yaoxinxin.imagescan.utils.NativeImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yaoxinxin on 16/3/11.
 */
public class ImageBeanAdapter extends RecyclerView.Adapter<ImageBeanAdapter.MyViewHolder> {

    private LayoutInflater mInflater;

    private Context mContext;

    private RecyclerView mView;

    private List<ImageBean> data;

//    public static enum TYPE {
//        ITEM_TYPE_IAMGE,
//        ITEM_TYPE_TEXT;
//    }

//    @Override
//    public int getItemViewType(int position) {
//        return position % 2 == 0 ? TYPE.ITEM_TYPE_IAMGE.ordinal() : TYPE.ITEM_TYPE_TEXT.ordinal();
//    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ImageBeanAdapter(Context c, List<ImageBean> data, RecyclerView view) {
        super();
        this.mContext = c;
        this.mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mView = view;
        this.data = data;
    }


    @Override
    public ImageBeanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.item_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(final ImageBeanAdapter.MyViewHolder holder, final int position) {
        ImageBean bean = data.get(position);
        holder.mTitle.setText(bean.getFolderName());
        holder.mCount.setText(bean.getCount() + "");
        holder.mTopImage.setTag(bean.getTopImagePath());


        NativeImageLoader loader = NativeImageLoader.getInstance();
        Bitmap bitmap = loader.loadImage(bean.getTopImagePath(), new Point(270, 270), new NativeImageLoader.ImageCallBack() {
            @Override
            public void callback(String url, Bitmap bitmap) {
                ImageView mImageView = (ImageView) mView.findViewWithTag(url);
                if (url.equals((String) holder.mTopImage.getTag())) {
                    mImageView.setImageBitmap(bitmap);
                } else {
                    mImageView.setImageResource(R.mipmap.ic_launcher);
                }
            }
        });

        if (bitmap == null) {
            holder.mTopImage.setImageResource(R.mipmap.ic_launcher);
        } else {
            holder.mTopImage.setImageBitmap(bitmap);
        }

        holder.mFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    //自定义ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.frame)
        FrameLayout mFrame;

        @Bind(R.id.top_image)
        ImageView mTopImage;

        @Bind(R.id.image_count)
        TextView mCount;

        @Bind(R.id.group_title)
        TextView mTitle;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface OnMyRecycleViewItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public OnMyRecycleViewItemClickListener listener;

    public void setOnMyRecycleViewItemClickListener(OnMyRecycleViewItemClickListener listener) {
        this.listener = listener;
    }


}
