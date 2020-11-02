package com.alan.handsome.module.main.presenter;

import com.alan.handsome.base.BasePresenter;
import com.alan.handsome.module.main.constant.PayOrderConstant;
import com.alan.handsome.net.RetrofitManger;
import com.alan.handsome.utils.RxSchedulers;

public class PayOrderPresenter  extends BasePresenter<PayOrderConstant.View> implements PayOrderConstant.Presenter{

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
