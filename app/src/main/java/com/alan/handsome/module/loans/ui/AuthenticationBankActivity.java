package com.alan.handsome.module.loans.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthenticationBankActivity extends BaseActivity {
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

    }

    @Override
    protected BaseContract.BasePresenter createPresenter() {
        return null;
    }

    @OnClick({R.id.ifsc_code_edit, R.id.bank_name_edit, R.id.bank_num_edit, R.id.Next_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ifsc_code_edit:
                break;
            case R.id.bank_name_edit:
                break;
            case R.id.bank_num_edit:
                break;
            case R.id.Next_tv:
                break;
        }
    }
}
