package com.alan.handsome.module.main.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseContract;
import com.alan.handsome.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment {
    @BindView(R.id.adfb_rl)
    RecyclerView recyclerView;
    @BindView(R.id.adfb_srl)
    SmartRefreshLayout smartRefreshLayout;

    private List<String> list;
    private LoansAdapter loansAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected BaseContract.BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(View view) {

        list=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("");
        }
        loansAdapter=new LoansAdapter();
        loansAdapter.setNewData(list);
        recyclerView.setAdapter(loansAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

}
