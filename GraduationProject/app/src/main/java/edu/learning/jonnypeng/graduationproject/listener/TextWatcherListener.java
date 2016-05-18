package edu.learning.jonnypeng.graduationproject.listener;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

/**
 * Created by jonny.peng on 2016/4/22 10:57.
 * Email:Jonny.peng@tinno.com
 */
public class TextWatcherListener implements TextWatcher {
    private TextView mTextView;

    public TextWatcherListener(TextView textView) {
        this.mTextView = textView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count > 0) {
            mTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
