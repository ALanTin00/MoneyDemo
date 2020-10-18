package com.alan.handsome.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alan.handsome.R;


/**
 * @author liufe
 */
public abstract class Loading extends Dialog {
    private TextView tv;
    /**
     * 取消dialog
     */
    private ImageView iv;

    public abstract void overShow();

    public Loading(Context context) {
        super(context, R.style.Loading);
        setContentView(R.layout.common_loading);
        tv = findViewById(R.id.tv);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setCancelable(false);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    public void show() {
        super.show();
        tv.setVisibility(View.GONE);
    }

    public void show(String text) {
        show();
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onBackPressed() {
        // 回调
        overShow();
        // 关闭Loading
        dismiss();
    }
}