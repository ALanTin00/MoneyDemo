package com.alan.handsome.module.loans.ui;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.alan.handsome.R;
import com.alan.handsome.user.DictsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class InfoAdapter extends BaseQuickAdapter<DictsBean, BaseViewHolder> {
    private String selectPhone;


    public InfoAdapter() {
        super(R.layout.item_select_list_view);
    }

    public void setSelect(String selectPhone) {
        this.selectPhone = selectPhone;
    }

    @Override
    protected void convert(BaseViewHolder helper, DictsBean item) {
        helper.setText(R.id.select_list_tv, item.getName());
        if (item.getName().equals(selectPhone)) {
            helper.setTextColor(R.id.select_list_tv, ContextCompat.getColor(mContext, R.color.color_33));
        } else {
            helper.setTextColor(R.id.select_list_tv, ContextCompat.getColor(mContext, R.color.color_99));
        }
    }
}