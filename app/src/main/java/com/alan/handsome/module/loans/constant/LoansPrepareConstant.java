package com.alan.handsome.module.loans.constant;

import com.alan.handsome.base.BaseContract;
import com.alan.handsome.module.loans.bean.LoansBean;
import com.alan.handsome.user.UserInformation;

import java.util.Map;

public class LoansPrepareConstant {

    public interface View extends BaseContract.BaseView {

        void getProductSuc(LoansBean loansBean);
        void getProductFail(String msg);

    }

    public interface Presenter {
        //获取首页信息
        void getProduct();

    }

}
