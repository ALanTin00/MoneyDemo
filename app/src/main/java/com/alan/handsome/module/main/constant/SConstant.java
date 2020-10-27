package com.alan.handsome.module.main.constant;

import com.alan.handsome.base.BaseContract;
import com.alan.handsome.user.SystemInfo;

public class SConstant {

    public interface View extends BaseContract.BaseView {

        void getSysInfoSuc(SystemInfo systemInfo);
        void getSysInfoFail(String msg);

    }

    public interface Presenter {
        //发送短信
        void getSysInfo();
    }

}
