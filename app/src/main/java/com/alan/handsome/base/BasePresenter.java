package com.alan.handsome.base;

import com.alan.handsome.base.bean.BaseMode;
import com.alan.handsome.manager.AccountManager;
import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * desc:
 * author: Will .
 * date: 2017/9/2 .
 */

public class BasePresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {

    protected T mView;

    @Override
    public void attachView(T view) {
        this.mView = view;

    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }

    public void loadUserInfo() {
       /* if (AccountManager.getInstance().isUserLogin()) {
            RetrofitManger.getInstance().create().getLoginUserInfo(0).compose(RxSchedulers.applySchedulers())
                    .compose(mView.bindToLife()).subscribe(objectBaseMode -> {
                if (objectBaseMode.isSuccess()) {
                    UserInformation userInformation = objectBaseMode.getResult();
                    userInformation.initResult();
                    AccountManager.getInstance().saveUserInfo(userInformation);
                    mView.loadUserSuccess(userInformation);
                }
            }, throwable -> mView.hideDialog());
        }*/
    }

    public <T> void add(Observable<BaseMode<T>> observable, YRequestCallback callback) {
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .compose(mView.bindToLife()).subscribe(t -> {
            if (t.getStatus()==1) {
                if (callback != null) {
                    callback.onSuccess(t.getResult());
                }
                //todo 超时操作
//            } else if (t.getError().getCode() == 401) {
//                AccountManager.getInstance().logout();
//                if (callback != null) {
//                    //t.getResult().getState()
//                    callback.onFailed(0, t.getError().getMessage());
//                }
            } else {
                if (callback != null) {
                    //t.getResult().getState()
                    callback.onFailed(0, t.getMessage());
                }
            }
        }, throwable -> {
            if (callback != null) {
                callback.onException(throwable);
            }
        });
    }

    public static <T> void staticAdd(Observable<BaseMode<T>> observable, YRequestCallback callback, LifecycleTransformer<BaseMode<T>> lifecycleTransformer) {
        if (lifecycleTransformer != null) {
            observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .compose(lifecycleTransformer).subscribe(t -> {
                if (t.getStatus()==1) {
                    if (callback != null) {
                        callback.onSuccess(t.getResult());
                    }
                    //todo 超时操作
//                } else if (t.getError().getCode() == 401) {
//                    AccountManager.getInstance().logout();
//                    if (callback != null) {
//                        //t.getResult().getState()
//                        callback.onFailed(0, t.getError().getMessage());
//                    }
                } else {
                    if (callback != null) {
                        callback.onFailed(0, t.getMessage());
                    }
                }
            }, throwable -> {
                if (callback != null) {
                    callback.onException(throwable);
                }
            });
        } else {
            observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(t -> {
                        if (t.getStatus()==1) {
                            if (callback != null) {
                                callback.onSuccess(t.getResult());
                            }
                            //todo 超时操作
//                        } else if (t.getError().getCode() == 401) {
//                            AccountManager.getInstance().logout();
//                            if (callback != null) {
//                                //t.getResult().getState()
//                                callback.onFailed(0, t.getError().getMessage());
//                            }
                        } else {
                            if (callback != null) {
                                callback.onFailed(0, t.getMessage());
                            }
                        }
                    }, throwable -> {
                        if (callback != null) {
                            callback.onException(throwable);
                        }
                    });
        }
    }
}