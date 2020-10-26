package com.alan.handsome.module.main.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;

import butterknife.BindView;
import butterknife.OnClick;

public class BasicInformation extends BaseActivity {
    @BindView(R.id.full_name_edit)
    EditText fullNameEdit;
    @BindView(R.id.birthday_tv)
    TextView birthdayTv;
    @BindView(R.id.gender_tv)
    TextView genderTv;
    @BindView(R.id.marital_tv)
    TextView maritalTv;
    @BindView(R.id.education_tv)
    TextView educationTv;
    @BindView(R.id.e_mail_edit)
    EditText eMailEdit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basic_information;
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

    @OnClick({R.id.birthday_relate, R.id.gender_relate, R.id.marital_relate, R.id.education_relate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.birthday_relate:
                break;
            case R.id.gender_relate:
                break;
            case R.id.marital_relate:
                break;
            case R.id.education_relate:
                break;
        }
    }
}
