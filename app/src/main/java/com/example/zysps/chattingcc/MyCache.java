package com.example.zysps.chattingcc;
import android.content.Context;

import com.netease.nimlib.sdk.StatusBarNotificationConfig;

/**
 * Created by zysps on 2017/6/7 0007.
 */
public class MyCache {
    private static Context context;

    private static String account;

    private static StatusBarNotificationConfig config;

    public static void clear() {
        account = null;
    }
    //获取账号信息
    public static String getAccount() {
        return account;
    }
    //设置账号信息
    public static void setAccount(String account) {
        MyCache.account = account;
    }
    //获取上下文
    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MyCache.context = context.getApplicationContext();
    }

    public static StatusBarNotificationConfig getConfig() {
        return config;
    }

    public static void setConfig(StatusBarNotificationConfig config) {
        MyCache.config = config;
    }
}
