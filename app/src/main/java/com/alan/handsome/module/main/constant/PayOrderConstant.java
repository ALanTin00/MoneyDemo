package com.alan.handsome.module.main.constant;

import com.alan.handsome.base.BaseContract;
import com.alan.handsome.module.loans.bean.LoansBean;

public class PayOrderConstant {

    public interface View extends BaseContract.BaseView {

        void getProductSuc(LoansBean loansBean);
        void getProductFail(String msg);

    }

    public interface Presenter {
        //获取产品信息
        void getProduct();

    }

}
