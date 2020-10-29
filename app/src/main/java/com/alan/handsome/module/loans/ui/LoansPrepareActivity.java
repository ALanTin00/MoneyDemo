package com.alan.handsome.module.loans.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.module.loans.bean.LimitsBean;
import com.alan.handsome.module.loans.bean.LoansBean;
import com.alan.handsome.module.loans.constant.LoansPrepareConstant;
import com.alan.handsome.module.loans.presenter.LoansPreparePresenter;
import com.alan.handsome.module.main.ui.MainActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页(还没付费)
 */
public class LoansPrepareActivity extends BaseActivity<LoansPreparePresenter> implements LoansPrepareConstant.View {
    @BindView(R.id.money_tv)
    TextView moneyTv;
    @BindView(R.id.money_recycler)
    RecyclerView moneyRecycler;

    private List<LimitsBean> list;
    private LoansAdapter loansAdapter;

    private boolean isFirst = true;

    public int selectPosition = 0;//选择的额度
    private int certification;//认证状态（0 认证完成 1 baseinfo 2 workinfo 3 bankinfo）
    private int phase; //	用户状态（0 未认证 1 审核中 2 审核通过 3 已缴费）

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loans_prepare;
    }

    @Override
    protected void initStatusBar() {
        super.initStatusBar();
        mImmersionBar = ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(R.color.color_55EDCF);
        mImmersionBar.init();

    }

    @Override
    protected void initView() {
//        showDialog();
    }

    @Override
    protected void initData() {
        //初始化
        list = new ArrayList<>();
        loansAdapter = new LoansAdapter();
        loansAdapter.setNewData(list);
        moneyRecycler.setAdapter(loansAdapter);
        moneyRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        //item点击事件
        loansAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (selectPosition == position) {
                    return;
                }
                selectPosition = position;
                for (int i = 0; i < list.size(); i++) {
                    if (position == i) {
                        list.get(i).setSelect(true);
                    } else {
                        list.get(i).setSelect(false);
                    }
                }
                moneyTv.setText("₹" + list.get(selectPosition).getAmount());
                loansAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        showDialog();
        //请求产品信息
        mPresenter.getProduct();
    }

    @Override
    protected LoansPreparePresenter createPresenter() {
        return new LoansPreparePresenter();
    }

    @OnClick(R.id.get_money_new_tv)
    public void onViewClicked() {
        //跳转
        switch (phase) {
            case 0:
                // 用户未认证
                if (certification == 1) {
                    //基础信息
                    startToActivity(AuthenticationBaseActivity.class);
                } else if (certification == 2) {
                    //工作信息
                    startToActivity(AuthenticationWorkActivity.class);
                } else if (certification == 3) {
                    //银行信息
                    startToActivity(AuthenticationBankActivity.class);
                }

                break;
            case 1:
                //审核中
                startToActivity(CheckActivity.class);
                break;
            case 2:
                //审核通过(只展示一次审核通过页面后面都调支付页面)
                if (AccountManager.getInstance().getUserInformation().isSeePassType()){
                    //跳转支付页面
                    startToActivity(PayOrderActivity.class);
                }else {
                    //跳转审核通过页面
                    startToActivity(PassSuccessActivity.class);
                }
                break;
            case 3:
                //已付款
                startToActivity(MainActivity.class);
                finish();
                break;
        }
    }

    //获取产品信息
    @Override
    public void getProductSuc(LoansBean loansBean) {
        //第一个默认选中
        if (loansBean != null & loansBean.getLimits().size() > 0) {
            this.phase = loansBean.getPhase();
            this.certification = loansBean.getCertification();
            if (isFirst) {
                isFirst = false;
                loansBean.getLimits().get(0).setSelect(true);
                moneyTv.setText("₹" + loansBean.getLimits().get(0).getAmount());
                list.addAll(loansBean.getLimits());
            }
        }
        loansAdapter.notifyDataSetChanged();
        hideDialog();
    }

    @Override
    public void getProductFail(String msg) {
        hideDialog();
        showErrorToast(msg);
    }

    //监听返回键
    @Override
    public void onBackPressed() {
        hideDialog();
        moveTaskToBack(true);
    }

}
