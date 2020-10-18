package com.alan.handsome.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.alan.handsome.R;

/**
 * 创建人: liufe
 * 时间:  2017/3/21
 * 描述:弹窗基类
 */

public abstract class BaseDialogFragment extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog;
        if (setDefaultTransparent()) {
            dialog = new Dialog(getActivity(), R.style.BottomTransparentDialog);
        } else {
            dialog = new Dialog(getActivity(), R.style.BottomDialog);
        }


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setCancelable(setDefaultCauseAble());
        dialog.setContentView(setContentLayout());
        // 外部点击取消
        dialog.setCanceledOnTouchOutside(setDefaultCauseAble());

        Window window = dialog.getWindow();
        WindowManager m = getActivity().getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = window.getAttributes();
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        if (setGravityCentre()) {
            p.width = (int) (d.getWidth() * setDialogWith());
            p.gravity = Gravity.CENTER;
        } else {
            p.width = WindowManager.LayoutParams.MATCH_PARENT;
            p.gravity = Gravity.BOTTOM;
        }

//        dialog.setOnDismissListener(dialog12 -> {
//            dialogDismiss();
//        });

        window.setAttributes(p);
        dialog.setOnKeyListener((dialog1, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return !setDefaultClickBack();
            }
            return false;
        });
        initView(dialog);
        return dialog;
    }

    protected float setDialogWith() {
        return 1.0f;
    }

    protected boolean setDefaultTransparent() {
        return true;
    }

    protected boolean setDefaultCauseAble() {
        return true;
    }

    protected boolean setDefaultClickBack() {
        return true;
    }

    protected boolean setGravityCentre() {
        return true;
    }

    protected  void dialogDismiss(){}

    /**
     * 设置布局
     *
     * @return
     */
    protected abstract int setContentLayout();

    /**
     * 初始化
     *
     * @param dialog
     */
    protected abstract void initView(Dialog dialog);


}
