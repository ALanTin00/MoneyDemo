package com.alan.handsome.module.loans.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;
import com.alan.handsome.widget.TitleBar;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthenticationBaseActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.line_one_tv)
    TextView lineOneTv;
    @BindView(R.id.line_two_tv)
    TextView lineTwoTv;
    @BindView(R.id.pass_one_tv)
    TextView passOneTv;
    @BindView(R.id.pass_two_tv)
    TextView passTwoTv;
    @BindView(R.id.pass_three_tv)
    TextView passThreeTv;
    @BindView(R.id.full_name_edit)
    EditText fullNameEdit;
    @BindView(R.id.e_mail_edit)
    EditText eMailEdit;
    @BindView(R.id.birthday_tv)
    TextView birthdayTv;
    @BindView(R.id.gender_tv)
    TextView genderTv;
    @BindView(R.id.marital_tv)
    TextView maritalTv;
    @BindView(R.id.education_tv)
    TextView educationTv;

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
    protected int getLayoutId() {
        return R.layout.activity_authentication_base;
    }

    @Override
    protected void initView() {
        passOneTv.setSelected(true);
        passOneTv.setText("1");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BaseContract.BasePresenter createPresenter() {
        return null;
    }

    @OnClick({R.id.birthday_tv, R.id.gender_tv, R.id.marital_tv, R.id.education_tv, R.id.Next_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.birthday_tv:
                break;
            case R.id.gender_tv:
                break;
            case R.id.marital_tv:
                break;
            case R.id.education_tv:
                break;
            case R.id.Next_tv:
                startToActivity(AuthenticationWorkActivity.class);
                break;
        }
    }
}
