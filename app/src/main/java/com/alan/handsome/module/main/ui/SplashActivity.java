package com.alan.handsome.module.main.ui;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.module.loans.ui.LoansPrepareActivity;
import com.alan.handsome.module.loans.ui.LoginActivity;
import com.alan.handsome.module.main.constant.SConstant;
import com.alan.handsome.module.main.presenter.SPresenter;
import com.alan.handsome.user.SystemInfo;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

public class SplashActivity extends BaseActivity<SPresenter> implements SConstant.View {

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
        if (AccountManager.getInstance().isUserLogin()){

            switch (AccountManager.getInstance().getUserInformation().getAuthorized()) {
                case 0:
                    //已认证
                    startToActivity(MainActivity.class);
                    break;
                case 1: ;
                case 2:
                case 3:
                    //跳转认证页面
                    startToActivity(LoansPrepareActivity.class);
                    break;
            }


        }else {
            startToActivity(LoginActivity.class);
        }

        finish();
    }

    @Override
    public void getSysInfoFail(String msg) {
        showErrorToast(msg);
    }
}
