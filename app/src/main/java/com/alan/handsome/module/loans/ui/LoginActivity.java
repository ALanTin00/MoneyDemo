package com.alan.handsome.module.loans.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.module.loans.bean.LoansBean;
import com.alan.handsome.module.loans.constant.LoginConstant;
import com.alan.handsome.module.loans.presenter.LoginPresenter;
import com.alan.handsome.module.main.ui.MainActivity;
import com.alan.handsome.user.UserInformation;
import com.alan.handsome.widget.CodeCountDownTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginConstant.View {
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.code_edit)
    EditText codeEdit;
    @BindView(R.id.code_count_down)
    CodeCountDownTextView codeCountDown;

    private Map<String, Object> loginMap;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        loginMap = new HashMap<>();
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @OnClick({R.id.code_count_down, R.id.login_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //发送验证码
            case R.id.code_count_down:
                if (TextUtils.isEmpty(phoneEdit.getText().toString())) {
                    showErrorToast("Please input your phone number");
                    return;
                }
                codeCountDown.startCountDown();
                mPresenter.sedCode(phoneEdit.getText().toString().trim());
                break;

            //登录
            case R.id.login_tv:
                if (TextUtils.isEmpty(phoneEdit.getText().toString())) {
                    showErrorToast("Please input your phone number");
                    return;
                }

                if (TextUtils.isEmpty(codeEdit.getText().toString())) {
                    showErrorToast("Please input verification code");
                    return;
                }
                showDialog();
                loginMap.put("mobile", phoneEdit.getText().toString().trim());
                loginMap.put("code", codeEdit.getText().toString().trim());
                mPresenter.login(loginMap);
                break;
        }
    }

    //发送验证码
    @Override
    public void sedCodeSuccess() {
        showErrorToast("Verification code sent successfully");
    }

    @Override
    public void sedCodeFail(String msg) {
        showErrorToast(msg);
        codeCountDown.stop();
    }

    //登录
    @Override
    public void loginSuccess(UserInformation userInformation) {

        AccountManager.getInstance().saveUserInfo(userInformation);
        startToActivity(MainActivity.class);

    }

    @Override
    public void loginFail(String msg) {
        hideDialog();
        showErrorToast(msg);
    }

    //获取审核状态(是否已经付费)
    @Override
    public void getProductSuc(LoansBean loansBean) {
        hideDialog();
        AccountManager.getInstance().saveAuthenticationType(loansBean.getPhase(), loansBean.getCertification());
        startToActivity(MainActivity.class);
        finish();
    }

    @Override
    public void getProductFail(String msg) {
        hideDialog();
        showErrorToast(msg);
    }

    //监听返回键
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}