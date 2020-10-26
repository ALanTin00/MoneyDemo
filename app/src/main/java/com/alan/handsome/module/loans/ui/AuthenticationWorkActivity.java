package com.alan.handsome.module.loans.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;
import com.alan.handsome.module.main.ui.BankInformationActivity;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthenticationWorkActivity extends BaseActivity {
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
    @BindView(R.id.employment_type_tv)
    TextView employmentTypeTv;
    @BindView(R.id.your_monthly_salary_tv)
    TextView yourMonthlySalaryTv;
    @BindView(R.id.monthly_family_income_tv)
    TextView monthlyFamilyIncomeTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authentication_work;
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

        lineOneTv.setSelected(true);
        passOneTv.setSelected(true);
        passTwoTv.setSelected(true);
        passTwoTv.setText("2");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BaseContract.BasePresenter createPresenter() {
        return null;
    }

    @OnClick({R.id.employment_type_tv, R.id.your_monthly_salary_tv, R.id.monthly_family_income_tv, R.id.Next_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.employment_type_tv:
                break;
            case R.id.your_monthly_salary_tv:
                break;
            case R.id.monthly_family_income_tv:
                break;
            case R.id.Next_tv:
                startToActivity(AuthenticationBankActivity.class);
                break;
        }
    }
}
