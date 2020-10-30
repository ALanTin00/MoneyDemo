package com.alan.handsome.net;

import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.manager.GlobalActionManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ObservableSubscribeHooker<T> implements Observer<T> {

    private Observer<T> actual;

    public ObservableSubscribeHooker(Observer observer) {
        actual = observer;
    }

    @Override
    public void onSubscribe(Disposable d) {
        actual.onSubscribe(d);
    }

    @Override
    public void onNext(T t) {
        actual.onNext(t);
    }

    @Override
    public void onError(Throwable e) {
//        if (e instanceof HttpException) {
//            String message = e.getMessage();
//            if (message.contains("Unauthorized")) {
//                actual.onError(new ApiException("token 过期"));
//                //  ActivityUtils.finishToActivity(LoginActivity.class, true);
//                AccountManager.getInstance().logout();
//                GlobalActionManager.getInstance().sendTokenINvalid("未登录或登录已过期");
//                return;
//            }
//            actual.onError(new ApiException("请求失败"+e.getMessage()));
//            return;
//        }
        //自己网络质量差
        if (e instanceof SocketTimeoutException) {
            actual.onError(new ApiException("The network connection is abnormal. Please check the network！"));
            return;
        }
        //服务器错误
        if (e instanceof ConnectException) {
            actual.onError(new ApiException("The network connection is abnormal. Please check the network！"));
            return;
        }
        //无法解析域名
        if (e instanceof UnknownHostException) {
            actual.onError(new ApiException("The network connection is abnormal. Please check the network！"));
            return;
        }

        if (e instanceof ApiException) {
            if (((ApiException) e).getCode() == 5 || ((ApiException) e).getCode() == 6) {
                actual.onError(e);
                AccountManager.getInstance().logout();
                GlobalActionManager.getInstance().sendTokenINvalid(e.getMessage());
                return;
            }
        }
//        if (e instanceof  Exception){
//            actual.onError(new ApiException("token 过期"));
//            ActivityUtils.finishToActivity(LoginActivity.class,true);
//            return;
//        }

        actual.onError(e);
    }

    @Override
    public void onComplete() {
        actual.onComplete();
    }
}
