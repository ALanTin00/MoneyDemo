package com.alan.handsome.module.main.ui;

import android.widget.EditText;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;

import butterknife.BindView;
import butterknife.OnClick;

public class BankInformationActivity extends BaseActivity {
    @BindView(R.id.ifsc_codeedit)
    EditText ifscCodeedit;
    @BindView(R.id.bank_name_edit)
    EditText bankNameEdit;
    @BindView(R.id.bank_account_edit)
    EditText bankAccountEdit;
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

    }

    @Override
    protected BaseContract.BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.save_tv)
    public void onViewClicked() {
    }
}
