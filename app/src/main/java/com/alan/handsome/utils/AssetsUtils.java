package com.alan.handsome.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by liufe on 2018/7/4
 */
public class AssetsUtils {

    private AssetsUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 从文件中获取字符串
     *
     * @param fileName
     * @param context
     * @return
     */
    public static String getString(String fileName, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 根据字符串返回实体类
     *
     * @param fileName
     * @param context
     * @param tClass
     * @param <T>      实体类
     * @return
     */
    public static <T> T getJsonObject(String fileName, Context context, Class<T> tClass) {
        String jsonStr = getString(fileName, context);
        return GsonUtils.convertObj(jsonStr, tClass);
    }

    /**
     * @param fileName
     * @param context
     * @param token
     * @return
     */
    public static <T> List<T> convertList(String fileName, Context context, TypeToken<List<T>> token) {
        String jsonStr = getString(fileName, context);
        return GsonUtils.convertList(jsonStr, token);
    }
}
