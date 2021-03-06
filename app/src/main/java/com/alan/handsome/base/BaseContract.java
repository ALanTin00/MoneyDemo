package com.alan.handsome.base;


import com.trello.rxlifecycle2.LifecycleTransformer;


public interface BaseContract {

    interface BasePresenter<T extends BaseView> {

        void attachView(T view);

        void detachView();

    }


    interface BaseView {

        void showErrorToast(String s);

        void showDialog();

        void hideDialog();

        /**
         * 绑定生命周期
         *
         * @param <T>
         * @return
         */
        <T> LifecycleTransformer<T> bindToLife();

        void tokenInvalid();

    }
}
