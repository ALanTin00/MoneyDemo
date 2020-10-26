package com.alan.handsome.module.main.ui;

import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class FeedBackActivity extends BaseActivity {
    @BindView(R.id.content_tv)
    TextView contentTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initStatusBar() {
        super.initStatusBar();
        mImmersionBar = ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(R.color.color_80F0D1);
        mImmersionBar.init();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        contentTv.setText("Any questions about the App, please contact us by E-mail, \n"
                + "\n" + "E-mail: service@xxCash.com");

    }

    @Override
    protected BaseContract.BasePresenter createPresenter() {
        return null;
    }

}