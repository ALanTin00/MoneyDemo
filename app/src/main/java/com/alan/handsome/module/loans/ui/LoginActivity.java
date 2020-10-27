package com.alan.handsome.module.loans.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;
import com.alan.handsome.widget.CodeCountDownTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.code_edit)
    EditText codeEdit;
    @BindView(R.id.code_count_down)
    CodeCountDownTextView codeCountDown;

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

    @OnClick({R.id.code_count_down, R.id.login_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //发送验证码
            case R.id.code_count_down:
                codeCountDown.startCountDown();
                showErrorToast("已发送");
                break;

            //登录
            case R.id.login_tv:
                break;
        }
    }
}
