package com.example.yaoxinxin.imagescan.support;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yaoxinxin.imagescan.R;
import com.example.yaoxinxin.imagescan.widget.CircleProgressView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContainFragment extends Fragment {

    private static final String TAG = ContainFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String mUrl;
    private String mPosition;

    @Bind(R.id.progressBar)
    CircleProgressView mProgrssBar;

    @Bind(R.id.error)
    TextView mError;

    @Bind(R.id.wait)
    TextView mWait;

//    @Bind(R.id.child)
//    FrameLayout mChild;

    @Bind(R.id.child)
    ImageView mChild;

    private boolean mFirstPageOn;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private OnFragmentInteractionListener mListener;

    public ContainFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContainFragment newInstance(String param1, String param2, String param3) {
        ContainFragment fragment = new ContainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(ARG_PARAM1);
            mPosition = getArguments().getString(ARG_PARAM2);
            mFirstPageOn = Boolean.getBoolean(getArguments().getString(ARG_PARAM3));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contain, container, false);
        ButterKnife.bind(this, view);

        view.setOnClickListener(new View.OnClickListener() {//退出
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        view.setOnLongClickListener(new LongClickListener(getActivity(), mUrl));

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        if (mFirstPageOn) {
            ((GalleryAnimationActivity) getActivity()).setAnimatorBackground().start();
            getArguments().putString(ARG_PARAM3, !mFirstPageOn + "");
        } else {
            ((GalleryAnimationActivity) getActivity()).setBackgroundImmediately();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        mWait.setVisibility(View.VISIBLE);

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).
                cacheOnDisk(true).bitmapConfig(Bitmap.Config.ARGB_8888).build();
        ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(mUrl), mChild, options,
                new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                mWait.setVisibility(View.INVISIBLE);
                mProgrssBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                mProgrssBar.setVisibility(View.INVISIBLE);
                mError.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mProgrssBar.setVisibility(View.INVISIBLE);
                mChild.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                mProgrssBar.setProgress(current);
            }
        });
//        Bitmap bitmap = NativeImageLoader.getInstance().loadImage(mUrl, null, new NativeImageLoader.ImageCallBack() {
//            @Override
//            public void callback(String url, Bitmap bitmap) {
////                ImageView imageView = new ImageView(getActivity());
////                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT
////                        , FrameLayout.LayoutParams.WRAP_CONTENT);
//                Log.e(TAG, mUrl);
//                if (bitmap != null) {
//                    mChild.setImageBitmap(bitmap);
//
//                } else {
//                    mChild.setImageResource(R.mipmap.ic_launcher);
//                }
//            }
//        });
//
//        mChild.setImageBitmap(bitmap);
    }

    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
