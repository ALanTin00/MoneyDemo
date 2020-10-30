package com.alan.handsome.net;

import android.content.Intent;
import android.util.Log;

import com.alan.handsome.BuildConfig;
import com.alan.handsome.base.App;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.module.loans.ui.LoginActivity;
import com.alan.handsome.module.main.ui.MainActivity;
import com.alan.handsome.utils.PackageUtil;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHeadInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder1 = request.newBuilder();
        //todo 在这里添加token
        builder1.addHeader("token",  AccountManager.getInstance().getAccessToken())
                .addHeader("os-type", "android")
                .addHeader("os-version", PackageUtil.getSystemVersion()) //ANDROID版本
                .addHeader("brand", PackageUtil.getDeviceBrand())//手机品牌
                .addHeader("model", PackageUtil.getSystemModel())//手机型号
                .addHeader("app-version", PackageUtil.getLocalVersionName(App.getApp()))//当前App版本号
                .addHeader("device-id", UUID.randomUUID().toString())//设备唯一ID，APP初始化时，自行生成一个UUID存储本地
//                .addHeader("adid", adjustadid)
//                .addHeader("gps-adid", gpsadid)
                .build();
        if (BuildConfig.LOG_DEBUG) {
            Log.i("=====>", "intercept: " + AccountManager.getInstance().getAccessToken());
        }
        List<String> headerValues = request.headers("url_name");

        Response response;
        if (headerValues != null && headerValues.size() > 0) {
            //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            builder1.removeHeader("url_name");

            //匹配获得新的BaseUrl
            String headerValue = headerValues.get(0);
            HttpUrl newBaseUrl = HttpUrl.parse(headerValue);

            //从request中获取原有的HttpUrl实例oldHttpUrl
            HttpUrl oldHttpUrl = request.url();
            //重建新的HttpUrl，修改需要修改的url部分
            HttpUrl newFullUrl = oldHttpUrl
                    .newBuilder()
                    .scheme(newBaseUrl.scheme())
                    .host(newBaseUrl.host())
                    .port(newBaseUrl.port())
                    .build();
            try {
                response = chain.proceed(builder1.url(newFullUrl).build());
            } catch (ConnectException e) {
                throw new ApiException(1, "The network connection is abnormal. Please check the network！");
            } catch (ConnectTimeoutException e2) {
                throw new ApiException(2, "The network connection is abnormal. Please check the network！");
            } catch (SocketTimeoutException | SocketException e3) {
                throw new ApiException(3, "The network connection is abnormal. Please check the network！");
            } catch (UnknownHostException e4) {
                throw new ApiException(4, "The network connection is abnormal. Please check the network！");
            }
        }else {
            response = chain.proceed(builder1.build());
        }

        if (response.code() == 401) {
            Intent intent = new Intent(App.getApp(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            App.getApp().startActivity(intent);
            AccountManager.getInstance().logout();
            throw new ApiException(5,"Log out or Login date");

        }
        return response;
    }
}
