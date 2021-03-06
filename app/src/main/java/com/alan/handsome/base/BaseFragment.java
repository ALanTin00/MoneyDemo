package com.alan.handsome.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lw on 2018/1/18.
 */

public abstract class BaseFragment<T extends BaseContract.BasePresenter> extends RxFragment implements BaseContract.BaseView {
    @Nullable
    protected T mPresenter;
    private Unbinder unbinder;
    private View mRootView;
    private Activity activity;

    protected abstract int getLayoutId();

    protected abstract T createPresenter();

    protected abstract void initView(View view);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public void showDialog() {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).showDialog("");
        }
    }

    @Override
    public void hideDialog() {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).hideDialog();
        }
    }

    @Override
    public void showErrorToast(String s) {
        if (s != null && s.length() > 0) {
            ToastUtils.setBgColor(Color.parseColor("#99000000"));
            ToastUtils.setGravity(Gravity.CENTER, 0, 0);
            ToastUtils.setMsgColor(Color.WHITE);
            ToastUtils.showShort(s);
        }
    }

    public Activity getActivity2() {
        return activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        attachView();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflaterView(inflater, container);
        unbinder = ButterKnife.bind(this, mRootView);
        initView(mRootView);
        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        detachView();
    }


    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }


    /**
     * 贴上view
     */
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 分离view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


    /**
     * 设置View
     *
     * @param inflater
     * @param container
     */
    private void inflaterView(LayoutInflater inflater, @Nullable ViewGroup container) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
    }



    @Override
    public void tokenInvalid() {

    }

    protected void startToActivity(Class<? extends Activity> activity) {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), activity);
            startActivity(intent);
        }
    }
}
