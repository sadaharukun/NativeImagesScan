package com.example.yaoxinxin.imagescan.support;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.yaoxinxin.imagescan.R;

import java.io.File;
import java.util.List;

/**
 * Created by yaoxinxin on 16/3/16.
 */
public class LongClickListener implements View.OnLongClickListener {

    private static final String TAG = LongClickListener.class.getSimpleName();

    private Context mC;

    private String url;

    private String[] choses;

    public LongClickListener(Context c, String path) {
        this.mC = c;
        this.url = path;
        this.choses = new String[]{getString(R.string.copy_link_to_clipboard), getString(R.string.share)};
    }


    @Override
    public boolean onLongClick(View v) {


        new AlertDialog.Builder(mC).setItems(choses, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        copyLink2Clipboard();
                        break;
                    case 1:
                        share();
                        break;
                }
            }
        }).setCancelable(true).show();


        return true;
    }

    /**
     * 分享
     */
    private void share() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        shareIntent.setType("image/jpeg");
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.fromFile(new File(url));
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            if (isIntentSafe(shareIntent)) {
                mC.startActivity(Intent.createChooser(shareIntent,
                        getString(R.string.share)));
            }
        }


    }

    /**
     * 复制2粘贴板
     */
    private void copyLink2Clipboard() {
        ClipboardManager manager = (ClipboardManager) mC.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText("imagescan", url));
        Toast.makeText(mC,
                getString(R.string.copy_successfully),
                Toast.LENGTH_SHORT).show();
    }


    private String getString(int resId) {
        return mC.getString(resId);
    }

    /**
     * @param intent
     * @return
     */
    private boolean isIntentSafe(Intent intent) {
        PackageManager packageManager = mC.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return activities.size() > 0;

    }
}
