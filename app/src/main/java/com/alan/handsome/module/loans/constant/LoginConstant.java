package com.alan.handsome.module.loans.constant;

import com.alan.handsome.base.BaseContract;
import com.alan.handsome.user.UserInformation;

import java.util.Map;

public class LoginConstant {


    public interface View extends BaseContract.BaseView {

        void sedCodeSuccess();
        void sedCodeFail(String msg);

        void loginSuccess(UserInformation userInformation);
        void loginFail(String msg);
    }

    public interface Presenter {
        //发送验证码
        void sedCode(String mobile);
        //登录
        void login(Map<String,Object> map);

    }

}
