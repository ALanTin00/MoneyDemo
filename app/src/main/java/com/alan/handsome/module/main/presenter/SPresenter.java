package com.alan.handsome.module.main.presenter;

import com.alan.handsome.base.BasePresenter;
import com.alan.handsome.module.main.constant.SConstant;
import com.alan.handsome.net.RetrofitManger;
import com.alan.handsome.utils.RxSchedulers;

public class SPresenter extends BasePresenter<SConstant.View>implements SConstant.Presenter {


    @Override
    public void getSysInfo() {
        RetrofitManger.getInstance().create().getSysInfo()
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(allBean -> {
                            if (allBean.getStatus()==1) {
                                mView.getSysInfoSuc(allBean.getResult());
                            } else {
                                mView.getSysInfoFail(allBean.getMessage());
                            }
                        }
                        , throwable -> {
                            mView.getSysInfoFail(throwable.getMessage());
                        });
    }
}
