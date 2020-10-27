package com.alan.handsome.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.alan.handsome.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 验证码倒计时
 */
public class CodeCountDownTextView extends AppCompatTextView {
    private int countDownL;
    private Disposable mSubscription;
    //验证码
    private String normalMessage = "Get verification code";
    private int time = 60;
    private String disableRegex = "Retry in sss";//不可点击显示 如ss秒后重新发送

    public CodeCountDownTextView(Context context) {
        super(context);
    }

    public CodeCountDownTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CodeCountDownTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setDisableRegex(String disableRegex) {
        this.disableRegex = disableRegex;
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CodeCountDownTextView);
        time = typedArray.getInt(R.styleable.CodeCountDownTextView_countdown, time);
        String temp = typedArray.getString(R.styleable.CodeCountDownTextView_normal_message);
        if (!TextUtils.isEmpty(temp)) {
            normalMessage = temp;
        }
        typedArray.recycle();
    }
    public void startCountDown() {
        setEnabled(false);
        if (mSubscription == null || mSubscription.isDisposed()) {
            countDownL = time;
            mSubscription = Observable.interval(1000, 1000, TimeUnit.MILLISECONDS, Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        countDownL--;
                        if (countDownL == 0) {
                            resetCountDown();
                        } else {
                            setText(disableRegex.replace("ss",Integer.toString(countDownL)));
                        }
                    }, throwable -> {

                    });
        }
    }

    private void resetCountDown() {
        setEnabled(true);
        if (mSubscription != null && !mSubscription.isDisposed()) {
            mSubscription.dispose();
        }
        setText(normalMessage);
        countDownL = time;
    }

    public void stop() {
        resetCountDown();
    }

    public void setTime(int time) {
        this.time = time;
    }
}
