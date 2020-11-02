package com.alan.handsome.module.loans.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.user.UserInformation;

import butterknife.BindView;
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

    private int selectLoanPosition;//首页进来传过来的

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pass;
    }

    @Override
    protected void initView() {
        //存储这个状态，这个页面只显示一次
        UserInformation userInformation= AccountManager.getInstance().getUserInformation();
        userInformation.setSeePassType(true);
        AccountManager.getInstance().saveUserInfo(userInformation);
    }

    @Override
    protected void initData() {
        selectLoanPosition = getIntent().getIntExtra("selectLoanPosition", -1);
        phoneTv.setText(AccountManager.getInstance().getUserInformation().getMobile());

        tipFourTv.setText("Your opplication has been approved and you are eligible to borrow");
        tipTwoTv.setText("become a member and just \nborrowed ₹ 100.000");
        if (!TextUtils.isEmpty(AccountManager.getInstance().getSysInfo().getTips_congratulations())){

            tipThreeTv.setText(AccountManager.getInstance().getSysInfo().getTips_congratulations());
        }
    }

    @Override
    protected BaseContract.BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.get_money_new_tv)
    public void onViewClicked() {
        Intent intent=new Intent(this,PayOrderActivity.class);
        intent.putExtra("selectLoanPosition",selectLoanPosition);
        startActivity(intent);
        finish();
    }
}
