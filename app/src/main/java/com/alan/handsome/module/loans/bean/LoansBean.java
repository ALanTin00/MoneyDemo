package com.alan.handsome.module.loans.bean;

import java.util.List;

public class LoansBean {

    private int certification;//认证状态（0 认证完成 1 baseinfo 2 workinfo 3 bankinfo）
    private int phase; //	用户状态（0 未认证 1 审核中 2 审核通过 3 已缴费）
    private List<LimitsBean> limits; //产品额度列表

    public int getCertification() {
        return certification;
    }

    public void setCertification(int certification) {
        this.certification = certification;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public List<LimitsBean> getLimits() {
        return limits;
    }

    public void setLimits(List<LimitsBean> limits) {
        this.limits = limits;
    }
}
