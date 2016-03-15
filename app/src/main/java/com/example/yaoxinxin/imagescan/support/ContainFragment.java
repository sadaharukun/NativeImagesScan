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
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.yaoxinxin.imagescan.R;
import com.example.yaoxinxin.imagescan.utils.NativeImageLoader;

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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mUrl;
    private String mPosition;

//    @Bind(R.id.progressBar)
//    ProgressBar mProgrssBar;

//    @Bind(R.id.error)
//    TextView mError;

//    @Bind(R.id.wait)
//    TextView mWait;

    @Bind(R.id.child)
    FrameLayout mChild;

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
    public static ContainFragment newInstance(String param1, String param2) {
        ContainFragment fragment = new ContainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(ARG_PARAM1);
            mPosition = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contain, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        NativeImageLoader.getInstance().loadImage(mUrl, null, new NativeImageLoader.ImageCallBack() {
            @Override
            public void callback(String url, Bitmap bitmap) {
                ImageView imageView = new ImageView(getActivity());
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT
                        , FrameLayout.LayoutParams.WRAP_CONTENT);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);

                } else {
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }
                imageView.setLayoutParams(params);
                mChild.addView(imageView);
            }
        });
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