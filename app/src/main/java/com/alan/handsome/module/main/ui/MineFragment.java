package com.alan.handsome.module.main.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseContract;
import com.alan.handsome.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {
    @BindView(R.id.user_head_iv)
    ImageView userHeadIv;
    @BindView(R.id.phone_tv)
    TextView phoneTv;

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

    }

    @OnClick({R.id.my_profile_lin, R.id.feedback_lin, R.id.customer_service_lin, R.id.about_us_lin, R.id.log_out_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_profile_lin:
                break;
            case R.id.feedback_lin:
                startToActivity(FeedBackActivity.class);
                break;
            case R.id.customer_service_lin:
                break;
            case R.id.about_us_lin:
                break;
            case R.id.log_out_lin:
                break;
        }
    }
}
