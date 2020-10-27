package com.alan.handsome.module.main.ui;

import android.view.View;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.module.loans.ui.LoansPrepareActivity;
import com.alan.handsome.module.main.constant.SConstant;
import com.alan.handsome.module.main.presenter.SPresenter;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import java.util.function.LongPredicate;

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
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToActivity(LoginActivity.class);
            }
        });

    }

    @Override
    protected SPresenter createPresenter() {
        return new SPresenter();
    }

    //发送验证码
    @Override
    public void sendMsgSuc(String result) {
    }

    @Override
    public void sendMsgFail(String msg) {
        showErrorToast(msg);
    }
}
