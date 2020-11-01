package com.alan.handsome.net;

import com.alan.handsome.BuildConfig;
import com.alan.handsome.base.bean.BaseMode;
import com.alan.handsome.module.loans.bean.LoansBean;
import com.alan.handsome.module.loans.bean.ReqBank;
import com.alan.handsome.module.loans.bean.ReqBase;
import com.alan.handsome.module.loans.bean.ReqWork;
import com.alan.handsome.module.main.bean.UserInfoBean;
import com.alan.handsome.user.SystemInfo;
import com.alan.handsome.user.UserInformation;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    /**
     * 提交基础信息
     * @return
     */
    @Headers({"url:" + BuildConfig.BASE_URL})
    @POST(UrlManager.URL_BASICS+"/profile/update_baseinfo")
    Observable<BaseMode<Object>> updateBaseInfo(@Body ReqBase reqBase);

    /**
     * 提交工作信息
     * @return
     */
    @Headers({"url:" + BuildConfig.BASE_URL})
    @POST(UrlManager.URL_BASICS+"/profile/update_workinfo")
    Observable<BaseMode<Object>> updateWorkInfo(@Body ReqWork reqWork);

    /**
     * 提交银行信息
     * @return
     */
    @Headers({"url:" + BuildConfig.BASE_URL})
    @POST(UrlManager.URL_BASICS+"/profile/update_bankinfo")
    Observable<BaseMode<Object>> updateBankInfo(@Body ReqBank reqBank);

    /**
     *获取用户信息
     * @return
     */
    @Headers({"url:" + BuildConfig.BASE_URL})
    @GET(UrlManager.URL_BASICS+"/profile")
    Observable<BaseMode<UserInfoBean>> getUserInfo();

    /**
     *反馈意见
     * @return
     */
    @Headers({"url:" + BuildConfig.BASE_URL})
    @POST(UrlManager.URL_BASICS+"/feedback")
    Observable<Map<String, Object>> feedBack(@Body Map<String,Object> reqMap);
}
