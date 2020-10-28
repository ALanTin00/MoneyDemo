package com.alan.handsome.module.loans.presenter;

import com.alan.handsome.base.BasePresenter;
import com.alan.handsome.module.loans.constant.LoansPrepareConstant;
import com.alan.handsome.module.loans.constant.LoginConstant;
import com.alan.handsome.net.RetrofitManger;
import com.alan.handsome.utils.RxSchedulers;

public class LoansPreparePresenter extends BasePresenter<LoansPrepareConstant.View> implements LoansPrepareConstant.Presenter{
    //获取产品信息
    @Override
    public void getProduct() {

        RetrofitManger.getInstance().create().getProduct()
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(allBean -> {
                            if (allBean.getStatus() == 1) {
                                mView.getProductSuc(allBean.getResult());
                            } else {
                                mView.getProductFail(allBean.getMessage());
                            }
                        }
                        , throwable -> {
                            mView.getProductFail(throwable.getMessage());
                        });

    }
}
