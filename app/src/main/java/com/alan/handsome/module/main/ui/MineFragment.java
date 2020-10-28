package com.alan.handsome.module.main.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseContract;
import com.alan.handsome.base.BaseFragment;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.module.loans.ui.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 我页面
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.user_head_iv)
    ImageView userHeadIv;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.customer_service_lin)
    LinearLayout customerServiceLin;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected BaseContract.BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(View view) {
        customerServiceLin.setVisibility(View.GONE);
    }

    @OnClick({R.id.my_profile_lin, R.id.feedback_lin, R.id.customer_service_lin, R.id.about_us_lin, R.id.log_out_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            //资料
            case R.id.my_profile_lin:
                startToActivity(MyProfileActivity.class);
                break;
            //反馈
            case R.id.feedback_lin:
                startToActivity(FeedBackActivity.class);
                break;
            //客服
            case R.id.customer_service_lin:
                break;
            //关于我们
            case R.id.about_us_lin:
                break;
            //登出
            case R.id.log_out_lin:
                AccountManager.getInstance().logout();
                getActivity2().finish();
                startToActivity(LoginActivity.class);
                break;
        }
    }

}
