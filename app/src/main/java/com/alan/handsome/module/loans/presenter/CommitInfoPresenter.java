package com.alan.handsome.module.loans.presenter;

import com.alan.handsome.base.BasePresenter;
import com.alan.handsome.module.loans.bean.ReqBank;
import com.alan.handsome.module.loans.bean.ReqBase;
import com.alan.handsome.module.loans.bean.ReqWork;
import com.alan.handsome.module.loans.constant.CommitInfoConstant;
import com.alan.handsome.module.loans.constant.LoginConstant;
import com.alan.handsome.net.RetrofitManger;
import com.alan.handsome.utils.RxSchedulers;

public class CommitInfoPresenter extends BasePresenter<CommitInfoConstant.View> implements CommitInfoConstant.Presenter{
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
