package com.alan.handsome.module.main.ui;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseContract;
import com.alan.handsome.base.BaseFragment;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.module.loans.ui.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我页面
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.user_head_iv)
    ImageView userHeadIv;
    @BindView(R.id.phone_tv)
    TextView phoneTv;

    private AlertDialog dialog;

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
        phoneTv.setText(AccountManager.getInstance().getUserInformation().getMobile());
    }

    @OnClick({R.id.my_profile_lin, R.id.feedback_lin, R.id.log_out_lin})
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
            //登出
            case R.id.log_out_lin:
                showSelectCarOrPhone();
                break;
        }
    }

    /**
     * 退出弹出框
     */
    public void showSelectCarOrPhone() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_logout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        TextView cancelTV = view.findViewById(R.id.dds_cancel_tv);
        TextView confirmTV = view.findViewById(R.id.dds_confirm_tv);

        cancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        confirmTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AccountManager.getInstance().logout();
                getActivity2().finish();
                startToActivity(LoginActivity.class);
            }
        });

        dialog = builder.create();
        dialog.setView(view);
        dialog.show();

    }

}
