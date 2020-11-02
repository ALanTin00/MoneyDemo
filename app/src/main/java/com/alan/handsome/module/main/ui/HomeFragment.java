package com.alan.handsome.module.main.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseFragment;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.module.loans.bean.LimitsBean;
import com.alan.handsome.module.loans.bean.LoansBean;
import com.alan.handsome.module.loans.bean.VipListBean;
import com.alan.handsome.module.main.constant.LoansPrepareConstant;
import com.alan.handsome.module.main.presenter.LoansPreparePresenter;
import com.alan.handsome.module.loans.ui.AuthenticationBankActivity;
import com.alan.handsome.module.loans.ui.AuthenticationBaseActivity;
import com.alan.handsome.module.loans.ui.AuthenticationWorkActivity;
import com.alan.handsome.module.loans.ui.CheckActivity;
import com.alan.handsome.module.loans.ui.FragmentCallback;
import com.alan.handsome.module.loans.ui.PassSuccessActivity;
import com.alan.handsome.module.loans.ui.PayOrderActivity;
import com.alan.handsome.user.UserInformation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页面
 */
public class HomeFragment extends BaseFragment<LoansPreparePresenter> implements LoansPrepareConstant.View {
    @BindView(R.id.adfb_rl)
    RecyclerView recyclerView;
    @BindView(R.id.adfb_srl)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.money_tv)
    TextView moneyTv;
    @BindView(R.id.money_recycler)
    RecyclerView moneyRecycler;
    @BindView(R.id.no_pay_lay)
    View noPayLay;
    @BindView(R.id.pay_lay)
    View payLay;

    //未支付的数据
    private List<LimitsBean> noPayList;
    private LoanAmountAdapter noPayAdapter;

    public int selectPosition = 0;//选择的额度
    private UserInformation userInfo;

    //已支付的数据
    private List<VipListBean> list;
    private LoansAdapter loansAdapter;

    //是否已经支付
    private boolean isPay = true;
    private FragmentCallback callback;

    private boolean isFirst = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected LoansPreparePresenter createPresenter() {
        return new LoansPreparePresenter();
    }

    public static HomeFragment newInstance(FragmentCallback fragmentCallback) {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setFragmentCallbackListener(fragmentCallback);
        return homeFragment;
    }

    public void setFragmentCallbackListener(FragmentCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void initView(View view) {
        showDialog();

        //初始化(未支付)
        noPayList = new ArrayList<>();
        noPayAdapter = new LoanAmountAdapter();
        noPayAdapter.setNewData(noPayList);
        moneyRecycler.setAdapter(noPayAdapter);
        moneyRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        //item点击事件
        noPayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (selectPosition == position) {
                    return;
                }
                selectPosition = position;
                for (int i = 0; i < noPayList.size(); i++) {
                    if (position == i) {
                        noPayList.get(i).setSelect(true);
                    } else {
                        noPayList.get(i).setSelect(false);
                    }
                }
                moneyTv.setText("₹" + noPayList.get(selectPosition).getAmount());
                noPayAdapter.notifyDataSetChanged();
            }
        });

        //初始化(已支付)
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableLoadMore(false);

        list = new ArrayList<>();
        loansAdapter = new LoansAdapter();
        loansAdapter.setNewData(list);
        recyclerView.setAdapter(loansAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //item点击
        loansAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.apply_iv) {
                    Uri uri = Uri.parse(list.get(position).getDownload_url());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });

        refreshInfo();
    }

    public void refreshInfo() {
        //请求产品列表
        mPresenter.getProduct();
    }

    //未支付按钮
    @OnClick(R.id.get_money_new_tv)
    public void onViewClicked() {
        userInfo = AccountManager.getInstance().getUserInformation();
        //跳转
        switch (userInfo.getPhase()) {
            case 0:
                // 用户未认证
                if (userInfo.getCertification() == 1) {
                    //基础信息
                    startToActivity(AuthenticationBaseActivity.class);
                } else if (userInfo.getCertification() == 2) {
                    //工作信息
                    startToActivity(AuthenticationWorkActivity.class);
                } else if (userInfo.getCertification() == 3) {
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
                if (AccountManager.getInstance().getUserInformation().isSeePassType()) {
                    //跳转支付页面
                    Intent intent=new Intent(getActivity(),PayOrderActivity.class);
                    intent.putExtra("selectLoanPosition",selectPosition);
                    startActivity(intent);
                } else {
                    //跳转审核通过页面
                    startToActivity(PassSuccessActivity.class);
                }
                break;
            case 3:
                //已付款
                break;
        }
    }

    //获取产品信息
    @Override
    public void getProductSuc(LoansBean loansBean) {

        if (loansBean != null) {
            isPay = loansBean.getPhase() == 3 ? true : false;
            noPayLay.setVisibility(isPay ? View.GONE : View.VISIBLE);
            payLay.setVisibility(isPay ? View.VISIBLE : View.GONE);
            callback.changBar(isPay);
            if (isPay) {
                if (loansBean.getViplist().size() > 0) {
                    list.addAll(loansBean.getViplist());
                }
                loansAdapter.notifyDataSetChanged();
            } else {

                AccountManager.getInstance().saveAuthenticationType(loansBean.getPhase(), loansBean.getCertification());
                if (loansBean.getLimits().size() > 0) {
                    if (isFirst) {
                        isFirst = false;
                        //循环出默认选项
                        for (int i = 0; i < loansBean.getLimits().size(); i++) {
                            if (loansBean.getLimits().get(i).getIs_default() == 1) {
                                selectPosition = i;
                                loansBean.getLimits().get(i).setSelect(true);
                                moneyTv.setText("₹" + loansBean.getLimits().get(i).getAmount());
                                noPayList.addAll(loansBean.getLimits());
                            }
                        }

                    }
                }
                noPayAdapter.notifyDataSetChanged();
            }
        }

        hideDialog();
    }

    @Override
    public void getProductFail(String msg) {
        hideDialog();
        showErrorToast(msg);
    }
}
