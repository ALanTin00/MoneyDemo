package com.alan.handsome.module.main.ui;

import com.alan.handsome.R;
import com.alan.handsome.module.loans.bean.LimitsBean;
import com.alan.handsome.module.loans.bean.LoansBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class LoanAmountAdapter extends BaseQuickAdapter<LimitsBean, BaseViewHolder> {

    public LoanAmountAdapter() {
        super(R.layout.adapter_loans_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, LimitsBean item) {

        helper.setText(R.id.content_tv, item.getAmount() + "");
        helper.getView(R.id.content_tv).setSelected(item.isSelect());
    }
}
