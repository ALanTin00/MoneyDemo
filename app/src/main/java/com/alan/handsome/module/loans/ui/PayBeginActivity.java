package com.alan.handsome.module.loans.ui;

import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.user.UserInformation;

import butterknife.BindView;
import butterknife.OnClick;

public class PayBeginActivity extends BaseActivity {
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
        //存储这个状态，这个页面只显示一次
        UserInformation userInformation= AccountManager.getInstance().getUserInformation();
        userInformation.setSeePassType(true);
        AccountManager.getInstance().saveUserInfo(userInformation);
    }

    @Override
    protected void initData() {
        phoneTv.setText(AccountManager.getInstance().getUserInformation().getMobile());

        tipFourTv.setText(AccountManager.getInstance().getSysInfo().getTips_congratulations());
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
