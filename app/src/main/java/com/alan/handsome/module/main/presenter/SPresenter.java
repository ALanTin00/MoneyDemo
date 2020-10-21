package com.alan.handsome.module.main.presenter;

import android.text.TextUtils;

import com.alan.handsome.R;
import com.alan.handsome.base.App;
import com.alan.handsome.base.BasePresenter;
import com.alan.handsome.module.main.constant.SConstant;
import com.alan.handsome.net.RetrofitManger;
import com.alan.handsome.utils.RxSchedulers;

public class SPresenter extends BasePresenter<SConstant.View>implements SConstant.Presenter {

    @Override
    public void sendMsg(String msg) {
        RetrofitManger.getInstance().create().sendMsg(msg)
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(allBean -> {
                            if (allBean.getStatus()==1) {
                                mView.sendMsgSuc(allBean.getMessage());
                            } else {
                              mView.sendMsgFail(allBean.getMessage());
                            }
                        }
                        , throwable -> {
                            mView.sendMsgFail(throwable.getMessage());
                        });
    }
}
