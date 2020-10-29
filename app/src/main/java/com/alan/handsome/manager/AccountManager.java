package com.alan.handsome.manager;

import android.text.TextUtils;

import com.alan.handsome.user.SystemInfo;
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
    private SystemInfo systemInfo;
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

    /**
     * 存储系统信息
     * @param result
     */
    public void saveSysInfo(SystemInfo result) {
        if (result != null) {
            systemInfo = result;
            SPUtils.getInstance().put("key_sys_info", GsonUtils.toJson(result));
        }
    }

    public SystemInfo getSysInfo() {
        if (systemInfo != null) {
            return systemInfo;
        }
        String userStr = SPUtils.getInstance().getString("key_sys_info");
        if (!TextUtils.isEmpty(userStr)) {
            systemInfo = GsonUtils.convertObj(userStr, SystemInfo.class);
            return systemInfo;
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
