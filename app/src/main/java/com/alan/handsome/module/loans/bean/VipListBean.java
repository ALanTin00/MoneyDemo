package com.alan.handsome.module.loans.bean;

public class VipListBean {

    private String id;
    private String cash_name;//名称
    private String download_url; //下载链接
    private String icon_url;//图标url
    private long min_amount;//最低额度
    private long max_amount;//最高额度
    private long min_interest;// 最低利息
    private long max_interest;//最高额度
    private long successful;//成功借款人数
    private long star;//星级
    private String is_best_recommend; //是否最佳推荐
    private String is_default;//是否默认
    private String is_high_quota;//是否高额度
    private String desc;//描述

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCash_name() {
        return cash_name;
    }

    public void setCash_name(String cash_name) {
        this.cash_name = cash_name;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public long getMin_amount() {
        return min_amount;
    }

    public void setMin_amount(long min_amount) {
        this.min_amount = min_amount;
    }

    public long getMax_amount() {
        return max_amount;
    }

    public void setMax_amount(long max_amount) {
        this.max_amount = max_amount;
    }

    public long getMin_interest() {
        return min_interest;
    }

    public void setMin_interest(long min_interest) {
        this.min_interest = min_interest;
    }

    public long getMax_interest() {
        return max_interest;
    }

    public void setMax_interest(long max_interest) {
        this.max_interest = max_interest;
    }

    public long getSuccessful() {
        return successful;
    }

    public void setSuccessful(long successful) {
        this.successful = successful;
    }

    public long getStar() {
        return star;
    }

    public void setStar(long star) {
        this.star = star;
    }

    public String getIs_best_recommend() {
        return is_best_recommend;
    }

    public void setIs_best_recommend(String is_best_recommend) {
        this.is_best_recommend = is_best_recommend;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getIs_high_quota() {
        return is_high_quota;
    }

    public void setIs_high_quota(String is_high_quota) {
        this.is_high_quota = is_high_quota;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
