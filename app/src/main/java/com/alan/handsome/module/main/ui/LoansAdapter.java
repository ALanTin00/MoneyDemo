package com.alan.handsome.module.main.ui;

import android.widget.ImageView;

import com.alan.handsome.R;
import com.alan.handsome.module.loans.bean.VipListBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.DecimalFormat;

public class LoansAdapter extends BaseQuickAdapter<VipListBean, BaseViewHolder> {

    public LoansAdapter() {
        super(R.layout.adater_loans);
    }

    @Override
    protected void convert(BaseViewHolder helper, VipListBean item) {
        Glide.with(mContext).load(item.getIcon_url())
                .apply(new RequestOptions().bitmapTransform(new CircleCrop()))
                .into((ImageView) helper.getView(R.id.head_iv));
        helper.setText(R.id.name_tv, item.getCash_name());
        helper.setText(R.id.content_tv, item.getDesc());

        helper.setText(R.id.loanable_tv, item.getMax_amount() + "\n" + "lloanable");
        helper.setText(R.id.Interest_tv, getNum(item.getMin_interest())+"~"+ getNum(item.getMax_interest())+ "\n" + "Interest");
        helper.setText(R.id.Successful_tv, item.getSuccessful() + "\n" + "Successful");
        helper.addOnClickListener(R.id.apply_iv);
    }

    public String getNum(long num) {
        DecimalFormat decimalFormat3 = new DecimalFormat("#0.00");
        return decimalFormat3.format((double) num / 100) + "%";
    }

}
