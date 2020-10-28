package com.alan.handsome.module.loans.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.module.loans.bean.LoansBean;
import com.alan.handsome.module.loans.constant.LoansPrepareConstant;
import com.alan.handsome.module.loans.presenter.LoansPreparePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 审核界面
 */
public class CheckActivity extends BaseActivity<LoansPreparePresenter> implements LoansPrepareConstant.View {
    @BindView(R.id.processing_iv)
    ImageView processingIv;
    @BindView(R.id.tip_one_tv)
    TextView tipOneTv;
    @BindView(R.id.tip_two_tv)
    TextView tipTwoTv;
    @BindView(R.id.refresh_tv)
    TextView refreshTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check;
    }

    @Override
    protected void initView() {
        processingIv.setVisibility(View.VISIBLE);
        tipOneTv.setText("Processing..");
        tipTwoTv.setText(AccountManager.getInstance().getSysInfo().getTips_processing());
        refreshTv.setText("Refresh");
    }

    @Override
    protected void initData() {
        mPresenter.getProduct();
    }

    @Override
    protected LoansPreparePresenter createPresenter() {
        return new LoansPreparePresenter();
    }

    @OnClick(R.id.refresh_tv)
    public void onViewClicked() {

        mPresenter.getProduct();

    }

    //获取审核状态
    @Override
    public void getProductSuc(LoansBean loansBean) {

        if (loansBean != null) {
            if (loansBean.getPhase() == 2) {
                //审核通过
                finish();
                startToActivity(PayBeginActivity.class);
            }
        }

    }

    @Override
    public void getProductFail(String msg) {

        showErrorToast(msg);
    }
}
