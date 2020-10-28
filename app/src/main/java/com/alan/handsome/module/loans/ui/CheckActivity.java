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
public class CheckActivity  extends BaseActivity<LoansPreparePresenter> implements LoansPrepareConstant.View {
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

    public static final int PROCESSING_TYPE = 101;//审核中页面
    public static final int CONGRATULATIONS_TYPE = 102;//审核通过页面

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
        setUI(type);
        if (type==PROCESSING_TYPE){
            showDialog();
            mPresenter.getProduct();
        }
    }

    //设置审核状态还是审核通过状态
    public void setUI(int type){
        if (type == PROCESSING_TYPE) {
            processingIv.setVisibility(View.VISIBLE);
            congratulationsIv.setVisibility(View.GONE);
            tipOneTv.setText("Processing..");
            tipTwoTv.setText(AccountManager.getInstance().getSysInfo().getTips_processing());
            refreshTv.setText("Refresh");
        } else {
            processingIv.setVisibility(View.GONE);
            congratulationsIv.setVisibility(View.VISIBLE);
            tipOneTv.setText("Congratulations!");
            tipTwoTv.setText(AccountManager.getInstance().getSysInfo().getTips_congratulations());
            refreshTv.setText("Continue");
        }
    }

    @Override
    protected LoansPreparePresenter createPresenter() {
        return new LoansPreparePresenter();
    }

    @OnClick(R.id.refresh_tv)
    public void onViewClicked() {

        if (type==PROCESSING_TYPE){
            showDialog();
            mPresenter.getProduct();
        }else {
            startToActivity(PayBeginActivity.class);
        }

    }

    //获取审核状态
    @Override
    public void getProductSuc(LoansBean loansBean) {

        if (loansBean!=null){
            if (loansBean.getPhase()==2){
                //审核通过
                setUI(CONGRATULATIONS_TYPE);
            }
        }

    }

    @Override
    public void getProductFail(String msg) {

        showErrorToast(msg);
    }
}
