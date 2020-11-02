package com.alan.handsome.module.main.ui;

import com.alan.handsome.R;
import com.alan.handsome.module.loans.bean.DurationsBean;
import com.alan.handsome.module.loans.bean.LimitsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class TermAdapter extends BaseQuickAdapter<DurationsBean, BaseViewHolder> {

    public TermAdapter() {
        super(R.layout.adapter_term);
    }

    @Override
    protected void convert(BaseViewHolder helper, DurationsBean item) {
        helper.getView(R.id.term_tv).setSelected(item.isSelect());
        helper.setText(R.id.term_tv,item.getDuration().substring(0,1));

    }
}
