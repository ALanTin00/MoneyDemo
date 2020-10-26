package com.alan.handsome.module.loans.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 审核界面
 */
public class CheckActivity extends BaseActivity {
    @BindView(R.id.processing_iv)
    ImageView processingIv;
    @BindView(R.id.tip_one_tv)
    TextView tipOneTv;
    @BindView(R.id.tip_two_tv)
    TextView tipTwoTv;
    @BindView(R.id.refresh_tv)
    TextView refreshTv;
    @BindView(R.id.congratulations_iv)
    ImageView congratulationsIv;

    public static final int PROCESSING_TYPE = 101;
    public static final int CONGRATULATIONS_TYPE = 102;

    private int type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", PROCESSING_TYPE);

        if (type == PROCESSING_TYPE) {
            processingIv.setVisibility(View.VISIBLE);
            congratulationsIv.setVisibility(View.GONE);
            tipOneTv.setText("Processing..");
            tipTwoTv.setText("your application information has been submitted," +
                    " we will complete the review of your information within," +
                    "3minutes,please pay attention in time");
            refreshTv.setText("Refresh");
        } else {
            processingIv.setVisibility(View.GONE);
            congratulationsIv.setVisibility(View.VISIBLE);
            tipOneTv.setText("Congratulations!");
            tipTwoTv.setText("Congratulations on passing the loan review, please click the button to get money");
            refreshTv.setText("Continue");
        }
    }

    @Override
    protected BaseContract.BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.refresh_tv)
    public void onViewClicked() {
    }

}
