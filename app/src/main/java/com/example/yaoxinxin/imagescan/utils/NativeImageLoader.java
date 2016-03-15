package com.example.yaoxinxin.imagescan.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yaoxinxin on 16/3/11.
 * <p/>
 * 加载本地图片工具类
 */
public class NativeImageLoader {

    private static final String TAG = NativeImageLoader.class.getSimpleName();

    private static final int IMAGE_LOAD = 0X11;

    /**
     * 内存缓存
     */
    private LruCache<String, Bitmap> mMemoryCache;

    private ExecutorService mThreadPool = Executors.newFixedThreadPool(2);

    //单例
    private static class Subclass {
        private static NativeImageLoader mInstance = new NativeImageLoader();
    }

    /**
     *  加载图片最大内存
     */
    private NativeImageLoader() {

        long maxMemory = Runtime.getRuntime().maxMemory();

        mMemoryCache = new LruCache<String, Bitmap>((int) (maxMemory / 4)) {

            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };


    }

    public static NativeImageLoader getInstance() {
        return Subclass.mInstance;
    }


    /**
     * 加载图片
     *
     * @param path
     * @param point
     * @param callBack
     */
    public Bitmap loadImage(final String path, final Point point, final ImageCallBack callBack) {

        //内存中获取
        final Bitmap bitmap = mMemoryCache.get(path);

        /**
         * 处理回调handler
         */
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == IMAGE_LOAD) {
                    callBack.callback(path, (Bitmap) msg.obj);
                }
            }
        };


        if (bitmap == null) {

            //本地获取
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    Message msg = Message.obtain();

                    Bitmap bitmap1 = getFromFile(path, point);

                    msg.what = IMAGE_LOAD;

                    msg.obj = bitmap1;

                    mHandler.sendMessage(msg);

                    addBitmap2MemoryCatch(path, bitmap1);
                }
            });

        }

        return bitmap;
    }

    /**
     * 添加bitmap到缓存
     *
     * @param path
     * @param bitmap1
     */
    private void addBitmap2MemoryCatch(String path, Bitmap bitmap1) {
        if (mMemoryCache.get(path) == null && bitmap1 != null) {
            mMemoryCache.put(path, bitmap1);
        }

    }

    /**
     * 本地加载
     *
     * @param path
     * @param point
     */
    private Bitmap getFromFile(String path, Point point) {
        int width = point == null ? 0 : point.x;
        int height = point == null ? 0 : point.y;

        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
        options.inSampleSize = 2;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
//        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);

    }

    /**
     * 本地加载
     *
     * @param path
     * @return
     */
    private Bitmap getFromFile(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(path, options);

    }


    /**
     * 等比缩放图片
     *
     * @param bitmap
     * @param width
     * @param height
     */
    private int scaleBitmap(Bitmap bitmap, int width, int height) {

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int scale = 1;

        if (w > width || h > height) {

            int scaleWidth = Math.round((float) w / (float) width);
            int scaleHeight = Math.round((float) h / (float) height);


            scale = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;
        }

        return scale;

    }


    /**
     * 加载图片回调接口
     */
    public interface ImageCallBack {

        void callback(String url, Bitmap bitmap);

    }

}
