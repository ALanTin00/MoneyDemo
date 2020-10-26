package com.alan.handsome.module.loans.ui;

import android.widget.EditText;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.phone_edit)
    EditText phoneEdit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
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

    @OnClick(R.id.Next_tv)
    public void onClick() {
        startToActivity(AuthenticationBaseActivity.class);
    }

}
