package com.alan.handsome.module.loans.ui;

<<<<<<< HEAD
=======
import android.text.TextUtils;
import android.view.View;
>>>>>>> origin/master
import android.widget.EditText;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
<<<<<<< HEAD
=======
    @BindView(R.id.code_edit)
    EditText codeEdit;
    @BindView(R.id.code_count_down)
    CodeCountDownTextView codeCountDown;
>>>>>>> origin/master

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

<<<<<<< HEAD
    @OnClick(R.id.Next_tv)
    public void onClick() {
        startToActivity(AuthenticationBaseActivity.class);
    }

=======
    @OnClick({R.id.code_count_down, R.id.login_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //发送验证码
            case R.id.code_count_down:
                if (TextUtils.isEmpty(phoneEdit.getText().toString())){
                    showErrorToast("Please input your phone number");
                    return;
                }
                codeCountDown.startCountDown();
                showErrorToast("已发送");
                break;

            //登录
            case R.id.login_tv:
                if (TextUtils.isEmpty(phoneEdit.getText().toString())){
                    showErrorToast("Please input your phone number");
                    return;
                }

                if (TextUtils.isEmpty(codeEdit.getText().toString())){
                    showErrorToast("Please input verification code");
                    return;
                }
                break;
        }
    }
>>>>>>> origin/master
}
