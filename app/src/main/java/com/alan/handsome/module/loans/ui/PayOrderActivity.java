package com.alan.handsome.module.loans.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.module.loans.bean.DurationsBean;
import com.alan.handsome.module.loans.bean.LimitsBean;
import com.alan.handsome.module.loans.bean.LoansBean;
import com.alan.handsome.module.main.constant.PayOrderConstant;
import com.alan.handsome.module.main.presenter.PayOrderPresenter;
import com.alan.handsome.module.main.ui.LoanAmountAdapter;
import com.alan.handsome.module.main.ui.TermAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

import butterknife.BindView;
import butterknife.OnClick;

public class PayOrderActivity extends BaseActivity<PayOrderPresenter> implements PayOrderConstant.View {
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

    //金额
    private List<LimitsBean> loanList;
    private LoanAmountAdapter loanAdapter;
    //周期
    private List<DurationsBean> termList;
    private TermAdapter termAdapter;

    private int selectLoanPosition;//看是否是首页进来的（首页进来会带值，没有就选默认）
    private int selectTermPosition = -1;//选择的周期


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
        showDialog();
    }

    @Override
    protected void initData() {
        selectLoanPosition = getIntent().getIntExtra("selectLoanPosition", -1);
        if (!TextUtils.isEmpty(AccountManager.getInstance().getSysInfo().getTips_pay())) {
            warmTv.setText(AccountManager.getInstance().getSysInfo().getTips_pay());
        }

        //初始化数据
        loanList = new ArrayList<>();
        loanAdapter = new LoanAmountAdapter();
        loanAdapter.setNewData(loanList);
        loanInterestRecycler.setAdapter(loanAdapter);
        loanInterestRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        //item点击事件
        loanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (selectLoanPosition == position) {
                    return;
                }
                //金额
                selectLoanPosition = position;
                for (int i = 0; i < loanList.size(); i++) {
                    if (position == i) {
                        loanList.get(i).setSelect(true);
                    } else {
                        loanList.get(i).setSelect(false);
                    }
                }
                loanAdapter.notifyDataSetChanged();
                termList.clear();
                termList.addAll(loanList.get(selectLoanPosition).getDurations());
                //周期
                for (int i = 0; i < termList.size(); i++) {
                    //循环默认选项
                    if (termList.get(i).getIs_default() == 1) {
                        selectTermPosition = i;
                        termList.get(i).setSelect(true);
                    } else {
                        selectTermPosition = -1;
                        termList.get(i).setSelect(false);
                    }
                }
                setTermUI();
                termAdapter.notifyDataSetChanged();
            }
        });

        termList = new ArrayList<>();
        termAdapter = new TermAdapter();
        termAdapter.setNewData(termList);
        termRecycler.setAdapter(termAdapter);
        termRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        //item点击事件
        termAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (selectTermPosition == position) {
                    return;
                }
                selectTermPosition = position;
                for (int i = 0; i < termList.size(); i++) {
                    if (position == i) {
                        termList.get(i).setSelect(true);
                    } else {
                        termList.get(i).setSelect(false);
                    }
                }
                setTermUI();
                termAdapter.notifyDataSetChanged();
            }
        });

        //请求产品信息
        mPresenter.getProduct();
    }

    /**
     * 设置贷款信息
     */
    public void setTermUI() {
        if (selectTermPosition == -1) {
            disbursalTv.setText("0");
            interestTv.setText("0");
            repaymentTv.setText("0");
            securityDepositTv.setText("0");
        } else {
            disbursalTv.setText(termList.get(selectTermPosition).getAmount() + "");
            interestTv.setText(termList.get(selectTermPosition).getInterest() + "");
            repaymentTv.setText(termList.get(selectTermPosition).getAmount() + termList.get(selectTermPosition).getInterest() + "");
            securityDepositTv.setText(termList.get(selectTermPosition).getMember_fee() + "");
        }

    }

    @Override
    protected PayOrderPresenter createPresenter() {
        return new PayOrderPresenter();
    }

    @OnClick(R.id.pay_now_tv)
    public void onViewClicked() {

        if (selectTermPosition == -1) {
            showErrorToast("Please select term");
            return;
        }
        showErrorToast("支付待接入");
        //todo 接了支付记得放开这个代码(支付成功调用)
//        EventBus.getDefault().post("success");
//        finish();
    }

    //获取产品信息
    @Override
    public void getProductSuc(LoansBean loansBean) {

        if (loansBean != null) {
            //金额
            loanList.addAll(loansBean.getLimits());

            if (selectLoanPosition == -1) {
                //循环默认选项
                for (int i = 0; i < loanList.size(); i++) {
                    if (loanList.get(i).getIs_default() == 1) {
                        selectLoanPosition = i;
                    }
                }
            }

            loanList.get(selectLoanPosition).setSelect(true);
            loanAdapter.notifyDataSetChanged();

            //周期
            termList.addAll(loansBean.getLimits().get(selectLoanPosition).getDurations());
            for (int i = 0; i < termList.size(); i++) {
                //循环默认选项
                if (termList.get(i).getIs_default() == 1) {
                    selectTermPosition = i;
                    termList.get(selectTermPosition).setSelect(true);
                }
            }
            setTermUI();
            termAdapter.notifyDataSetChanged();
        }

        hideDialog();
    }

    @Override
    public void getProductFail(String msg) {
        hideDialog();
        showErrorToast(msg);
    }
}
