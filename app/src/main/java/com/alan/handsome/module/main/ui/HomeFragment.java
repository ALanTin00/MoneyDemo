package com.alan.handsome.module.main.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseFragment;
import com.alan.handsome.module.loans.bean.LoansBean;
import com.alan.handsome.module.loans.bean.VipListBean;
import com.alan.handsome.module.loans.constant.LoansPrepareConstant;
import com.alan.handsome.module.loans.presenter.LoansPreparePresenter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 主页面
 */
public class HomeFragment extends BaseFragment<LoansPreparePresenter> implements LoansPrepareConstant.View {
    @BindView(R.id.adfb_rl)
    RecyclerView recyclerView;
    @BindView(R.id.adfb_srl)
    SmartRefreshLayout smartRefreshLayout;

    private List<VipListBean> list;
    private LoansAdapter loansAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected LoansPreparePresenter createPresenter() {
        return new LoansPreparePresenter();
    }

    @Override
    protected void initView(View view) {
        showDialog();
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableLoadMore(false);

        //初始化
        list = new ArrayList<>();
        loansAdapter = new LoansAdapter();
        loansAdapter.setNewData(list);
        recyclerView.setAdapter(loansAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //item点击
        loansAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId()==R.id.apply_iv){
                    showErrorToast("apply");
                }
            }
        });

        //请求产品列表
        mPresenter.getProduct();
    }

    //获取产品信息
    @Override
    public void getProductSuc(LoansBean loansBean) {
        if (loansBean != null && loansBean.getViplist().size() > 0) {
            list.addAll(loansBean.getViplist());
        }
        loansAdapter.notifyDataSetChanged();
        hideDialog();
    }

    @Override
    public void getProductFail(String msg) {
        hideDialog();
        showErrorToast(msg);
    }
}
