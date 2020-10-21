package com.alan.handsome.module.main.constant;

import com.alan.handsome.base.BaseContract;

import java.util.Map;

public class SConstant {

    public interface View extends BaseContract.BaseView {

        void sendMsgSuc(String result);
        void sendMsgFail(String msg);

    }

    public interface Presenter {
        //发送短信
        void sendMsg(String msg);
    }

}
