package com.alan.handsome.module.main.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkInfoActivity extends BaseActivity {
    @BindView(R.id.gender_tv)
    TextView genderTv;
    @BindView(R.id.your_monthly_salary_tv)
    TextView yourMonthlySalaryTv;
    @BindView(R.id.monthly_family_income_tv)
    TextView monthlyFamilyIncomeTv;
    @BindView(R.id.save_tv)
    TextView saveTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_information;
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

    @OnClick({R.id.employment_type_relate, R.id.your_monthly_salary_relate, R.id.monthly_family_income_relate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.employment_type_relate:
                break;
            case R.id.your_monthly_salary_relate:
                break;
            case R.id.monthly_family_income_relate:
                break;
        }
    }
}
