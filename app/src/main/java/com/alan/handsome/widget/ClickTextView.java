package com.alan.handsome.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/6/28.
 */

public class ClickTextView extends AppCompatTextView {
    private List<TextView> editTextList;
    private CheckBox checkBox;
    public ClickTextView(Context context) {
        super(context);
    }

    public ClickTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bindCheckBox(CheckBox box) {
        checkBox = box;
    }

    public void bindEditText(TextView... editTexts) {
        if (editTexts != null && editTexts.length > 0) {
            editTextList = new ArrayList<>();
            for (TextView editText : editTexts) {
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        checkInput();
                    }
                });
                editTextList.add(editText);
            }
        }
        checkInput();
    }

    public void checkInput() {
        boolean enable = true;
        if (editTextList != null && editTextList.size() > 0) {
            for (TextView editText : editTextList) {
                if (editText.getText() == null || editText.getText().length() <= 0) {
                    enable = false;
                    setEnabled(false);
                }
            }
        }
        if (enable) {
            if (checkBox != null && !checkBox.isChecked()) {
                setEnabled(false);
            }else {
                setEnabled(true);
            }
        }
    }
}
