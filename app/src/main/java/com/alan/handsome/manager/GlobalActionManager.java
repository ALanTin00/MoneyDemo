package com.alan.handsome.manager;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：全局行为监听，如token过期，网络断开等
 * （网络请求写时未写错误的统一处理基类，用监听来替代）
 * 作者：qiujialiu
 * 时间：2018/7/10
 */

public class GlobalActionManager {
    private List<GlobalAction> actionList;

    private static class GlobalActionManagerHolder{
        private static final GlobalActionManager INSTANCE = new GlobalActionManager();
    }

    public static GlobalActionManager getInstance() {
        return GlobalActionManagerHolder.INSTANCE;
    }

    public interface GlobalAction{
        void onTokenInvalid(String message);
    }

    public void addListener(GlobalAction action) {
        if (actionList == null) {
            actionList = new ArrayList<>();
        }
        actionList.add(action);
    }

    public void removeListener(GlobalAction action) {
        if (actionList != null && actionList.size() > 0) {
            actionList.remove(action);
        }
    }

    public void sendTokenINvalid(String message) {
        if (actionList != null && actionList.size() > 0) {
            for (GlobalAction action : actionList) {
                action.onTokenInvalid(message);
            }
        }
    }
}
