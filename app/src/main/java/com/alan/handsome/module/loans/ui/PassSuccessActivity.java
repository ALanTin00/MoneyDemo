package com.alan.handsome.module.loans.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PassSuccessActivity extends BaseActivity {
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.tip_two_tv)
    TextView tipTwoTv;
    @BindView(R.id.tip_three_tv)
    TextView tipThreeTv;
    @BindView(R.id.tip_four_tv)
    TextView tipFourTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pass;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        tipFourTv.setText("Your opplication has been approved and you are eligible to borrow");
        tipTwoTv.setText("become a member and just \nborrowed ₹ 100.000");
        tipThreeTv.setText("Next, you need to become a member to get an installment loan." +
                " After becoming a member, you will also get very low loan interest");
    }

    @Override
    protected BaseContract.BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.get_money_new_tv)
    public void onViewClicked() {
    }
}
