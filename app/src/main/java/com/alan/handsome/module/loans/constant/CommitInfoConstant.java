package com.alan.handsome.module.loans.constant;

import com.alan.handsome.base.BaseContract;
import com.alan.handsome.module.loans.bean.ReqBank;
import com.alan.handsome.module.loans.bean.ReqBase;
import com.alan.handsome.module.loans.bean.ReqWork;

public class CommitInfoConstant {

    public interface View extends BaseContract.BaseView {

       void commitBaseInfoSuc();
       void commitBaseInfoFail(String msg);

        void commitWorkInfoSuc();
        void commitWorkInfoFail(String msg);

        void commitBankSuc();
        void commitBankFail(String msg);

    }

    public interface Presenter {
        //提交基础信息
        void commitBaseInfo(ReqBase reqBase);
        //提交工作信息
        void commitWorkInfo(ReqWork reqWork);
        //提交银行信息
        void commitBankInfo(ReqBank reqBank);

    }

}