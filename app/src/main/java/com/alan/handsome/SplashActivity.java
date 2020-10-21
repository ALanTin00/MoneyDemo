package com.alan.handsome;

import android.text.TextUtils;
import android.view.View;

import com.alan.handsome.module.main.constant.SConstant;
import com.alan.handsome.module.main.presenter.SPresenter;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.alan.handsome.base.BaseActivity;

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
                mPresenter.sendMsg("810222");
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
        if (!TextUtils.isEmpty(result)) {
            showErrorToast("成功");
        }
    }

    @Override
    public void sendMsgFail(String msg) {
        showErrorToast(msg);
    }
}
