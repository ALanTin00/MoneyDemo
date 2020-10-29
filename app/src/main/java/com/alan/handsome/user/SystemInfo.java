package com.alan.handsome.user;

import java.util.List;

/**
 * 系统参数
 */
public class SystemInfo {

    private String pay_channel;//支付渠道
    private String sys_service_email; //	客服Email
    private String sys_service_time;//客服时间
    private String tips_processing;//审核页文案
    private String tips_congratulations;//审核成功页文案
    private String tips_pay;//支付页文案
    private List<DictsBean> dicts;

    public String getSys_service_email() {
        return sys_service_email;
    }

    public void setSys_service_email(String sys_service_email) {
        this.sys_service_email = sys_service_email;
    }

    public String getSys_service_time() {
        return sys_service_time;
    }

    public void setSys_service_time(String sys_service_time) {
        this.sys_service_time = sys_service_time;
    }

    public String getPay_channel() {
        return pay_channel;
    }

    public void setPay_channel(String pay_channel) {
        this.pay_channel = pay_channel;
    }

    public String getTips_processing() {
        return tips_processing;
    }

    public void setTips_processing(String tips_processing) {
        this.tips_processing = tips_processing;
    }

    public String getTips_congratulations() {
        return tips_congratulations;
    }

    public void setTips_congratulations(String tips_congratulations) {
        this.tips_congratulations = tips_congratulations;
    }

    public String getTips_pay() {
        return tips_pay;
    }

    public void setTips_pay(String tips_pay) {
        this.tips_pay = tips_pay;
    }

    public List<DictsBean> getDicts() {
        return dicts;
    }

    public void setDicts(List<DictsBean> dicts) {
        this.dicts = dicts;
    }

}
