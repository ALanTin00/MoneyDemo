package com.alan.handsome.module.main.ui;

import android.view.View;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;
import com.gyf.barlibrary.ImmersionBar;
import butterknife.OnClick;

public class MyProfileActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_profile;
    }

    @Override
    protected void initStatusBar() {
        super.initStatusBar();
        mImmersionBar = ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(R.color.white);
        mImmersionBar.init();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected BaseContract.BasePresenter createPresenter() {
        return null;
    }


    @OnClick({R.id.basic_information_relate, R.id.work_information_relate, R.id.bank_information_relate})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.basic_information_relate:
                startToActivity(BaseInformationActivity.class);

                break;
            case R.id.work_information_relate:
                startToActivity(WorkInfoActivity.class);
                break;

            case R.id.bank_information_relate:
                startToActivity(BankInformationActivity.class);
                break;
        }
    }
}
