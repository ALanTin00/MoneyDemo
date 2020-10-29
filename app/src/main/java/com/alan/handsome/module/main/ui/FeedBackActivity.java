package com.alan.handsome.module.main.ui;

import android.text.TextUtils;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;
import com.alan.handsome.manager.AccountManager;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class FeedBackActivity extends BaseActivity {
    @BindView(R.id.content_tv)
    TextView contentTv;

    public static final int FEEDBACK_TYPE = 105;
    public static final int ABOUT_US_TYPE = 106;
    private int type;

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
        type = getIntent().getIntExtra("type", FEEDBACK_TYPE);
        String content = "";
        if (!TextUtils.isEmpty(AccountManager.getInstance().getSysInfo().getSys_service_email())) {
            content = AccountManager.getInstance().getSysInfo().getSys_service_email();
        }

        contentTv.setText(type == FEEDBACK_TYPE ?
                "Any questions about the App, please contact us by E-mail, \n"
                        + "\n" + "E-mail: " + content : content);

    }

    @Override
    protected BaseContract.BasePresenter createPresenter() {
        return null;
    }

}
