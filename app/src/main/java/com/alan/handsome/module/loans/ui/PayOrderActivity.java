package com.alan.handsome.module.loans.ui;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;

public class PayOrderActivity extends BaseActivity {
    @BindView(R.id.loan_interest_recycler)
    RecyclerView loanInterestRecycler;
    @BindView(R.id.term_recycler)
    RecyclerView termRecycler;
    @BindView(R.id.disbursal_tv)
    TextView disbursalTv;
    @BindView(R.id.interest_tv)
    TextView interestTv;
    @BindView(R.id.repayment_tv)
    TextView repaymentTv;
    @BindView(R.id.security_deposit_tv)
    TextView securityDepositTv;
    @BindView(R.id.warm_tv)
    TextView warmTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_order;
    }

    @Override
    protected void initStatusBar() {
        super.initStatusBar();
        mImmersionBar = ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(R.color.color_32DDAE);
        mImmersionBar.init();

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        warmTv.setText("You need a bit of pay to get this loan, if you fail, it will be returned in 5 days.");
    }

    @Override
    protected BaseContract.BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.pay_now_tv)
    public void onViewClicked() {
    }
}
