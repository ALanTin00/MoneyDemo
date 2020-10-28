package com.alan.handsome.net;

import com.alan.handsome.BuildConfig;
import com.alan.handsome.base.bean.BaseMode;
import com.alan.handsome.module.loans.bean.LoansBean;
import com.alan.handsome.user.SystemInfo;
import com.alan.handsome.user.UserInformation;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author liufe
 * @time 2018/6/26 13:23
 */
public interface ApiService {

    /**
     * 登录
     * @return
     */
    @Headers({"url:" + BuildConfig.BASE_URL})
    @POST(UrlManager.URL_BASICS+"/user/login")
    Observable<BaseMode<UserInformation>> login(@Body Map<String, Object> map);

    /**
     * 发送验证码
     * @return
     */
    @Headers({"url:" + BuildConfig.BASE_URL})
    @GET(UrlManager.URL_BASICS+"/user/sms")
    Observable<BaseMode<Object>> sendMsg(@Query("mobile") String mobile);

    /**
     * 获取系统参数
     * @return
     */
    @Headers({"url:" + BuildConfig.BASE_URL})
    @GET(UrlManager.URL_BASICS+"/config")
    Observable<BaseMode<SystemInfo>> getSysInfo();

    /**
     * 产品首页
     * @return
     */
    @Headers({"url:" + BuildConfig.BASE_URL})
    @GET(UrlManager.URL_BASICS+"/product")
    Observable<BaseMode<LoansBean>> getProduct();

}
