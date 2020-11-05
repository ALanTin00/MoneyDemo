package com.alan.handsome.net;


import android.util.Log;


import com.alan.handsome.BuildConfig;
import com.alan.handsome.manager.AccountManager;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *
 * @author liufe
 *
 * @date 2018/4/8
 *
 * @des
 *
 * <pre>
 */
public class HttpHeadInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder1 = request.newBuilder();
        builder1.addHeader("Authorization","Bearer "+ AccountManager.getInstance().getAccessToken()).build();
        if (BuildConfig.LOG_DEBUG) {
            Log.i("=====>", "intercept: " + AccountManager.getInstance().getAccessToken());
        }
        List<String> headerValues = request.headers("url");
        Response response;
        if (headerValues != null && headerValues.size() > 0) {
            //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            builder1.removeHeader("url");

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
                throw new ApiException(1, "网络断开连接，请检查网络！");
            } catch (ConnectTimeoutException e2) {
                throw new ApiException(2, "网络断开连接，请检查网络！");
            } catch (SocketTimeoutException | SocketException e3) {
                throw new ApiException(3, "网络断开连接，请检查网络！");
            } catch (UnknownHostException e4) {
                throw new ApiException(4, "网络断开连接，请检查网络！");
            }
        } else {
            response = chain.proceed(builder1.build());
        }

        if (response.code() == 401) {
            //token验证过期
//            throw new ApiException(6, "该账号已在别处登录");
        }
        return response;
    }
}
