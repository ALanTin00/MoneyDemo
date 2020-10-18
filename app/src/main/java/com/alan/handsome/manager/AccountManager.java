package com.alan.handsome.manager;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.alan.handsome.constant.Constant;
import com.alan.handsome.user.TokenModel;
import com.alan.handsome.user.UserInformation;
import com.alan.handsome.utils.GsonUtils;

/**
 * 类说明：用户账号管理
 * 作者：qiujialiu
 * 时间：2018/6/28
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

    public void saveToken(TokenModel result) {
        if (result != null) {
            accessToken = result.getAccessToken();
            SPUtils.getInstance().put("key_access_token", accessToken);
            SPUtils.getInstance().put("key_refresh_token", result.getEncryptedAccessToken());
            SPUtils.getInstance().put("key_token_over_time", System.currentTimeMillis() + (result.getExpireInSeconds() * 1000L));
            SPUtils.getInstance().put(Constant.AUTHENTICATE_TOKEN_ISNOLOGIN, result.isNoLogin());
        }
    }

    /**
     * 登录是否过期
     *
     * @return
     */
    public boolean isTokenOverTime() {
        return System.currentTimeMillis() > SPUtils.getInstance().getLong("key_token_over_time", 0L);
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
        }
    }


    public void logout() {
        userInformation = null;
        accessToken = null;
        SPUtils.getInstance().remove("key_user_info");
        SPUtils.getInstance().remove("key_access_token");
        SPUtils.getInstance().remove("key_refresh_token");
        SPUtils.getInstance().remove("key_token_over_time");
        SPUtils.getInstance().remove("key_user_setting");
        SPUtils.getInstance().remove(Constant.AUTHENTICATE_TOKEN_ISNOLOGIN);
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
    public boolean isUserLogin() {
        return !AccountManager.getInstance().isTokenOverTime() && AccountManager.getInstance().getUserInformation() != null;
    }
}
