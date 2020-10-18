package com.alan.handsome.net;

import android.util.Log;

import com.alan.handsome.BuildConfig;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.alan.handsome.BuildConfig.LOG_DEBUG;

/**
 * @author liufe
 * @time 2018/6/26 13:23
 */
public class RetrofitManger {

    ApiService apiService;

    private RetrofitManger() {
        apiService = new Retrofit.Builder().baseUrl(BuildConfig.URL_REPORT)
                .client(provideClient(providerHttpLoggingInterceptor()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiService.class);

    }


    public static RetrofitManger getInstance() {
        return RetrofitMangerHolder.INSTANCE;
    }

    public ApiService create() {
        return apiService;
    }

    /**
     * 创建日志拦截器
     *
     * @return
     */
    HttpLoggingInterceptor providerHttpLoggingInterceptor() {
        HttpLoggingInterceptor.Logger logger = message -> {
            if (LOG_DEBUG) {
                Log.i("http:", message);
            }

        };
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    /**
     * OkHttpClient
     *
     * @param logger
     * @return
     */
    OkHttpClient provideClient(HttpLoggingInterceptor logger) {
        //设置超时
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.proxy(Proxy.NO_PROXY);
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(15, TimeUnit.SECONDS);
        builder.addInterceptor(new HttpHeadInterceptor());
        builder.addInterceptor(logger);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    private static class RetrofitMangerHolder {
        public static final RetrofitManger INSTANCE = new RetrofitManger();
    }
}
