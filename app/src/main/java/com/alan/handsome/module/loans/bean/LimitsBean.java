package com.alan.handsome.module.loans.bean;

import java.util.List;

public class LimitsBean {

    private long amount; //产品额度
    private int is_default; //	默认值
    private boolean isSelect;//是否选中
    private List<DurationsBean> durations; //产品周期列表
    private List<VipListBean> viplist;//vip产品列表

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public List<VipListBean> getViplist() {
        return viplist;
    }

    public void setViplist(List<VipListBean> viplist) {
        this.viplist = viplist;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public List<DurationsBean> getDurations() {
        return durations;
    }

    public void setDurations(List<DurationsBean> durations) {
        this.durations = durations;
    }

}
