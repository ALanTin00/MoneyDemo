package com.alan.handsome.module.main.ui;

import android.view.View;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseContract;
import com.alan.handsome.base.BaseFragment;

public class MineFragment extends BaseFragment {
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

    }
}
