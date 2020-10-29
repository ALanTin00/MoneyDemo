package com.alan.handsome.module.main.ui;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.module.loans.bean.ReqBank;
import com.alan.handsome.module.main.bean.UserInfoBean;
import com.alan.handsome.module.main.constant.SaveInfoConstant;
import com.alan.handsome.module.main.presenter.SaveInfoPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class BankInformationActivity extends BaseActivity<SaveInfoPresenter> implements SaveInfoConstant.View {
    @BindView(R.id.ifsc_codeedit)
    EditText ifscCodeEdit;
    @BindView(R.id.bank_name_edit)
    EditText bankNameEdit;
    @BindView(R.id.bank_account_edit)
    EditText bankNumEdit;
    @BindView(R.id.save_tv)
    TextView saveTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bank_information;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        //请求用户数据
        showDialog();
        mPresenter.getUserInfo();
    }

    @Override
    protected SaveInfoPresenter createPresenter() {
        return new SaveInfoPresenter();
    }

    //保存信息
    @OnClick(R.id.save_tv)
    public void onViewClicked() {
        if (TextUtils.isEmpty(ifscCodeEdit.getText().toString().trim())) {
            showErrorToast("Please input IFSC Code");
            return;
        }

        if (TextUtils.isEmpty(bankNameEdit.getText().toString().trim())) {
            showErrorToast("Please input Bank Name");
            return;
        }

        if (TextUtils.isEmpty(bankNumEdit.getText().toString().trim())) {
            showErrorToast("Please input Bank number");
            return;
        }

        //提交银行信息
        showDialog();
        ReqBank bank = new ReqBank();
        bank.setIfsc_code(ifscCodeEdit.getText().toString().trim());
        bank.setBank_name(bankNameEdit.getText().toString().trim());
        bank.setBank_account_no(bankNumEdit.getText().toString().trim());
        mPresenter.commitBankInfo(bank);
    }

    //h获取用户信息
    @Override
    public void getUserInfoSuc(UserInfoBean userInfoBean) {
        hideDialog();
        if (userInfoBean!=null){
            ifscCodeEdit.setText(userInfoBean.getIfsc_code());
            bankNameEdit.setText(userInfoBean.getBank_name());
            bankNumEdit.setText(userInfoBean.getBank_account_no());
        }

    }

    @Override
    public void getUserInfoFail(String msg) {
        hideDialog();
        showErrorToast(msg);
    }

    //提交基础信息
    @Override
    public void commitBaseInfoSuc() {

    }

    @Override
    public void commitBaseInfoFail(String msg) {

    }

    //提交工作信息
    @Override
    public void commitWorkInfoSuc() {

    }

    @Override
    public void commitWorkInfoFail(String msg) {

    }

    //提交银行信息
    @Override
    public void commitBankSuc() {
        hideDialog();
        showErrorToast("Save successfully");
        finish();
    }

    @Override
    public void commitBankFail(String msg) {
        hideDialog();
        showErrorToast(msg);
    }
}
