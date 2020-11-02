package com.alan.handsome.module.loans.bean;

public class DurationsBean {

    private String id;
    private long amount; //	产品额度
    private long interest; //总利息
    private String duration; //	周期文本
    private long monthly_payment; //月还款金额
    private long monthly_principal; //月还款本金
    private long monthly_inerest; //	月还款利息
    private long member_ori_fee; //	会员价（原价）
    private long member_fee;//会员价（折后价）
    private int is_default;//是否默认
    private boolean isSelect;//是否选择(自己添加)

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getInterest() {
        return interest;
    }

    public void setInterest(long interest) {
        this.interest = interest;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getMonthly_payment() {
        return monthly_payment;
    }

    public void setMonthly_payment(long monthly_payment) {
        this.monthly_payment = monthly_payment;
    }

    public long getMonthly_principal() {
        return monthly_principal;
    }

    public void setMonthly_principal(long monthly_principal) {
        this.monthly_principal = monthly_principal;
    }

    public long getMonthly_inerest() {
        return monthly_inerest;
    }

    public void setMonthly_inerest(long monthly_inerest) {
        this.monthly_inerest = monthly_inerest;
    }

    public long getMember_ori_fee() {
        return member_ori_fee;
    }

    public void setMember_ori_fee(long member_ori_fee) {
        this.member_ori_fee = member_ori_fee;
    }

    public long getMember_fee() {
        return member_fee;
    }

    public void setMember_fee(long member_fee) {
        this.member_fee = member_fee;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }
}
