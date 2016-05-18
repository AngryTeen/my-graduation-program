package edu.learning.jonnypeng.graduationproject.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import edu.learning.jonnypeng.graduationproject.R;

public class SimpleDialog {
    private static final String TAG = "SimpleDialog";
    Context mContext;
    AlertDialog builder;
    String str;

    public SimpleDialog(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * @param text Show Information
     */
    public void showTextDialog(String title, String text) {
        builder = new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(text)
                .setPositiveButton(mContext.getResources().getString(R.string.positive_button), null)
                .show();
    }


}