package com.alan.handsome.module.loans.presenter;

import com.alan.handsome.base.BasePresenter;
import com.alan.handsome.module.loans.constant.LoginConstant;
import com.alan.handsome.net.RetrofitManger;
import com.alan.handsome.utils.RxSchedulers;

import java.util.Map;

public class LoginPresenter extends BasePresenter<LoginConstant.View> implements LoginConstant.Presenter {

    //发送验证码
    @Override
    public void sedCode(String mobile) {
        RetrofitManger.getInstance().create().sendMsg(mobile)
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(allBean -> {
                            if (allBean.getStatus() == 1) {
                                mView.sedCodeSuccess();
                            } else {
                                mView.sedCodeFail(allBean.getMessage());
                            }
                        }
                        , throwable -> {
                            mView.sedCodeFail(throwable.getMessage());
                        });
    }

    //登录
    @Override
    public void login(Map<String, Object> map) {
        RetrofitManger.getInstance().create().login(map)
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(allBean -> {
                            if (allBean.getStatus() == 1) {
                                mView.loginSuccess(allBean.getResult());
                            } else {
                                mView.loginFail(allBean.getMessage());
                            }
                        }
                        , throwable -> {
                            mView.loginFail(throwable.getMessage());
                        });
    }

}
