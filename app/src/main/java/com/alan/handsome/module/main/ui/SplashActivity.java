package com.alan.handsome.module.main.ui;

import android.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.module.loans.ui.LoginActivity;
import com.alan.handsome.module.main.constant.SConstant;
import com.alan.handsome.module.main.presenter.SPresenter;
import com.alan.handsome.user.SystemInfo;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

public class SplashActivity extends BaseActivity<SPresenter> implements SConstant.View {
    private AlertDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initStatusBar() {
        mImmersionBar = ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_HIDE_BAR);
        mImmersionBar.init();
    }

    @Override
    protected void initData() {

        //请求系统参数
        mPresenter.getSysInfo();
    }

    @Override
    protected SPresenter createPresenter() {
        return new SPresenter();
    }

    //获取系统参数
    @Override
    public void getSysInfoSuc(SystemInfo systemInfo) {
        AccountManager.getInstance().saveSysInfo(systemInfo);
        if (AccountManager.getInstance().isUserLogin()) {

            startToActivity(MainActivity.class);

        } else {
            startToActivity(LoginActivity.class);
        }

        finish();
    }

    @Override
    public void getSysInfoFail(String msg) {
        showSelectCarOrPhone();
    }

    /**
     * 强制弹窗
     */
    public void showSelectCarOrPhone() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_sp, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        TextView confirmTV = view.findViewById(R.id.dds_confirm_tv);

        confirmTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mPresenter.getSysInfo();
            }
        });
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setView(view);
        dialog.show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;//返回false进行拦截点击事件
        }
        return super.onKeyDown(keyCode, event);
    }
}
