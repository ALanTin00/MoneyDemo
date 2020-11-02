package com.alan.handsome.module.loans.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.module.loans.bean.ReqBank;
import com.alan.handsome.module.loans.constant.CommitInfoConstant;
import com.alan.handsome.module.loans.presenter.CommitInfoPresenter;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthenticationBankActivity extends BaseActivity<CommitInfoPresenter> implements CommitInfoConstant.View {
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
    @BindView(R.id.ifsc_code_edit)
    EditText ifscCodeEdit;
    @BindView(R.id.bank_name_edit)
    EditText bankNameEdit;
    @BindView(R.id.bank_num_edit)
    EditText bankNumEdit;

    private int selectLoanPosition;//首页进来传过来的

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authentication_bank;
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
        lineTwoTv.setSelected(true);
        passOneTv.setSelected(true);
        passTwoTv.setSelected(true);
        passThreeTv.setSelected(true);
        passThreeTv.setText("3");
    }

    @Override
    protected void initData() {
        selectLoanPosition = getIntent().getIntExtra("selectLoanPosition", -1);
    }

    @Override
    protected CommitInfoPresenter createPresenter() {
        return new CommitInfoPresenter();
    }

    @OnClick({R.id.Next_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Next_tv:
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
                break;
        }
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
        AccountManager.getInstance().saveAuthenticationType(1,-1);
        Intent intent=new Intent(this,CheckActivity.class);
        intent.putExtra("selectLoanPosition",selectLoanPosition);
        startActivity(intent);
        finish();
    }

    @Override
    public void commitBankFail(String msg) {
        hideDialog();
        showErrorToast(msg);
    }

}
