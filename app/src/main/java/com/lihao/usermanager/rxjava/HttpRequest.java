package com.lihao.usermanager.rxjava;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lihao on 2016/12/12.
 */

public class HttpRequest {

    public static final String BASE_URL = "http://192.168.1.107:8080/start/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private WebService webService;

    private HttpRequest(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        webService = retrofit.create(WebService.class);
    }

    private static class SingletonHolder{
        private static final HttpRequest INSTANCE = new HttpRequest();
    }

    //获取单例
    public static HttpRequest getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void getUserInfoById(int userId, Subscriber<UserBean> subscriber){
        webService.queryUserById(userId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
