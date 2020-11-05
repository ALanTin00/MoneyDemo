package com.alan.handsome.manager;

import android.text.TextUtils;

import com.alan.handsome.user.UserInformation;
import com.alan.handsome.utils.GsonUtils;
import com.blankj.utilcode.util.SPUtils;

/**
 * 类说明：用户账号管理
 */

public class AccountManager {
    public static AccountManager instance;
    private String accessToken;
    private UserInformation userInformation;//账户主账号用户信息
    public synchronized static AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

    public void saveToken(UserInformation result) {
        if (result != null) {
            accessToken = result.getToken();
            SPUtils.getInstance().put("key_access_token", accessToken);
//            SPUtils.getInstance().put("key_refresh_token", result.getToken());
        }
    }


    public String getAccessToken() {
        if (!TextUtils.isEmpty(accessToken)) {
            return accessToken;
        } else {
            accessToken = SPUtils.getInstance().getString("key_access_token");
            return accessToken;
        }
    }

    public void saveUserInfo(UserInformation result) {
        if (result != null) {
            userInformation = result;
            SPUtils.getInstance().put("key_user_info", GsonUtils.toJson(result));
            saveToken(result);
        }
    }

    //存储审核状态值
    public void saveAuthenticationType(int phase,int certification){
        UserInformation userInfo=getUserInformation();
        if (phase!=-1){
            userInfo.setPhase(phase);
        }

        if (certification!=-1){
            userInfo.setCertification(certification);
        }
        saveUserInfo(userInfo);
    }

    public UserInformation getUserInformation() {
        if (userInformation != null) {
            return userInformation;
        }
        String userStr = SPUtils.getInstance().getString("key_user_info");
        if (!TextUtils.isEmpty(userStr)) {
            userInformation = GsonUtils.convertObj(userStr, UserInformation.class);
            return userInformation;
        }
        return null;
    }

    public void logout() {
        userInformation = null;
        accessToken = null;
        SPUtils.getInstance().remove("key_user_info");
        SPUtils.getInstance().remove("key_access_token");
    }

    public boolean isUserLogin() {
        return  AccountManager.getInstance().getUserInformation() != null &&
                AccountManager.getInstance().getAccessToken()!=null;
    }
}
