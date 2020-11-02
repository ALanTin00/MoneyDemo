package com.alan.handsome.module.main.presenter;

import com.alan.handsome.base.BasePresenter;
import com.alan.handsome.module.loans.bean.ReqBank;
import com.alan.handsome.module.loans.bean.ReqBase;
import com.alan.handsome.module.loans.bean.ReqWork;
import com.alan.handsome.module.main.constant.SaveInfoConstant;
import com.alan.handsome.net.RetrofitManger;
import com.alan.handsome.utils.RxSchedulers;

public class SaveInfoPresenter extends BasePresenter<SaveInfoConstant.View> implements SaveInfoConstant.Presenter{
    //获取用户信息
    @Override
    public void getUserInfo() {
        RetrofitManger.getInstance().create().getUserInfo()
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(allBean -> {
                            if (allBean.getStatus() == 1) {
                                mView.getUserInfoSuc(allBean.getResult());
                            } else {
                                mView.getUserInfoFail(allBean.getMessage());
                            }
                        }
                        , throwable -> {
                            mView.getUserInfoFail(throwable.getMessage());
                        });
    }
    //提交基础信息
    @Override
    public void commitBaseInfo(ReqBase reqBase) {
        RetrofitManger.getInstance().create().updateBaseInfo(reqBase)
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(allBean -> {
                            if (allBean.getStatus() == 1) {
                                mView.commitBaseInfoSuc();
                            } else {
                                mView.commitBaseInfoFail(allBean.getMessage());
                            }
                        }
                        , throwable -> {
                            mView.commitBaseInfoFail(throwable.getMessage());
                        });
    }

    //提交工作信息
    @Override
    public void commitWorkInfo(ReqWork reqWork) {
        RetrofitManger.getInstance().create().updateWorkInfo(reqWork)
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(allBean -> {
                            if (allBean.getStatus() == 1) {
                                mView.commitWorkInfoSuc();
                            } else {
                                mView.commitWorkInfoFail(allBean.getMessage());
                            }
                        }
                        , throwable -> {
                            mView.commitWorkInfoFail(throwable.getMessage());
                        });
    }

    //提交银行信息
    @Override
    public void commitBankInfo(ReqBank reqBank) {
        RetrofitManger.getInstance().create().updateBankInfo(reqBank)
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(allBean -> {
                            if (allBean.getStatus() == 1) {
                                mView.commitBankSuc();
                            } else {
                                mView.commitBankFail(allBean.getMessage());
                            }
                        }
                        , throwable -> {
                            mView.commitBankFail(throwable.getMessage());
                        });
    }
}
