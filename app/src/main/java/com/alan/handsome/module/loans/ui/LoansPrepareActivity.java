package com.alan.handsome.module.loans.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;
import com.alan.handsome.module.main.ui.MainActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LoansPrepareActivity extends BaseActivity {
    @BindView(R.id.money_tv)
    TextView moneyTv;
    @BindView(R.id.money_recycler)
    RecyclerView moneyRecycler;
    @BindView(R.id.select_money_tv)
    TextView selectMoneyTv;

    private List<String> list;
    private LoansAdapter loansAdapter;

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
        moneyTv.setText("₹100.0000");
    }

    @Override
    protected void initData() {

        list=new ArrayList<>();
        loansAdapter=new LoansAdapter();
        for (int i = 0; i < 4; i++) {
            list.add("");
        }
        loansAdapter.setNewData(list);
        moneyRecycler.setAdapter(loansAdapter);
        moneyRecycler.setLayoutManager(new GridLayoutManager(this,4));
        //item点击事件
        loansAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
    }

    @Override
    protected BaseContract.BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.get_money_new_tv)
    public void onViewClicked() {
      startToActivity(MainActivity.class);
    }
}
