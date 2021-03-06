package com.alan.handsome.base;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.alan.handsome.R;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.widget.Loading;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lw on 2018/1/18.
 */

public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends RxAppCompatActivity implements BaseContract.BaseView {
    @Nullable
    protected T mPresenter;
    private Unbinder unbinder;
    protected ImmersionBar mImmersionBar;
    protected FragmentManager fragmentManager;
//    protected UserInformation mUserInfo;
    Loading mLoading;
    public static float sNoncompatDensity;
    public static float sNoncompatScaledDensity;

    protected boolean isIgnoreKeyBoard = false;

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract T createPresenter();

    protected void setVustomDensity(@NonNull Activity activity, @NonNull final Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNoncompatDensity == 0) {
            Log.i("Density", "setVustomDensity: sNoncompatDensity=0 " + appDisplayMetrics);
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        final float targetDensity = appDisplayMetrics.heightPixels / 640;
        final float targetscaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);
        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetscaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;
        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetscaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
        Log.i("Density", "setVustomDensity: " + targetDensity);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        // setVustomDensity(this,App.getApp());
//        mUserInfo = AccountManager.getInstance().getUserInformation();
        setContentView(layoutId);
        initStatusBar();
        mPresenter = createPresenter();
        unbinder = ButterKnife.bind(this);
        attachView();
        mLoading = new Loading(this) {

            @Override
            public void overShow() {

            }
        };
        fragmentManager = getSupportFragmentManager();
        View view = findViewById(R.id.iv_back);
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        initView();
        initData();
    }

    protected void initStatusBar() {
        mImmersionBar = ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(R.color.white);
        mImmersionBar.init();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mUserInfo = AccountManager.getInstance().getUserInformation();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        unbinder.unbind();
        detachView();
    }


    @Override
    public void showErrorToast(String s) {
        hideDialog();
        if (s != null && s.length() > 0) {
            ToastUtils.setBgColor(Color.parseColor("#99000000"));
            ToastUtils.setMsgColor(Color.WHITE);
            ToastUtils.setGravity(Gravity.CENTER, 0, 0);
            ToastUtils.showShort(s);
        }
    }

    @Override
    public void showDialog() {
        if (mLoading != null) {
            try {
                if (mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                mLoading.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showDialog(String text) {
        if (mLoading != null) {
            try {
                if (mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                mLoading.show(text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.fontScale = 1.0f;
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    public void hideDialog() {
        if (mLoading != null) {
            mLoading.dismiss();
        }
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isIgnoreKeyBoard) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    KeyboardUtils.hideSoftInput(this);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        //判断得到的焦点控件是否包含EditText
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。 
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    /**
     * fragment 入栈
     *
     * @param fragment
     * @param tag
     */
    protected void push(Fragment fragment, String tag) {

        List<Fragment> currentFragments = fragmentManager.getFragments();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (currentFragments.size() != 0) {
            // 流程中，第一个步骤的 Fragment 进场不需要动画，其余步骤需要
            transaction.setCustomAnimations(
                    R.anim.push_in_left,
                    R.anim.push_out_left,
                    R.anim.push_in_right,
                    R.anim.push_out_right
            );
        }
        if (fragment.isAdded()) {
            if (currentFragments.size() != 0) {
                fragmentManager.popBackStack(tag, 0);
                transaction.show(fragment);
            }
        } else {
            transaction.add(R.id.root, fragment, tag);
            if (currentFragments.size() != 0) {
                transaction.hide(currentFragments.get(currentFragments.size() - 1)).addToBackStack(tag);
            }
        }

        transaction.commitAllowingStateLoss();
    }

    /**
     * fragment 获取
     *
     * @param fragmentClass
     * @param <T>
     * @return
     */
    protected <T extends Fragment> T findOrCreateFragment(@NonNull Class<T> fragmentClass) {
        String tag = fragmentClass.getCanonicalName();
        FragmentManager fragmentManager = getSupportFragmentManager();
        T fragment = (T) fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fragment;
    }

    protected void startToActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    @Override
    public void tokenInvalid() {

    }

//    @Override
//    public void loadUserSuccess(UserInformation userInformation) {
//        mUserInfo = userInformation;
//    }

    /**
     * 跳转到登录
     */
    public void startToLogin() {
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.push_in_bottom,R.anim.push_out_top);
    }

    /**
     * 校验登录，未登录跳转到登录页
     *
     * @return
     */
    public boolean checkLogin() {
        if (AccountManager.getInstance().isUserLogin()) {
            return true;
        } else {
            startToLogin();
            return false;
        }
    }
}
