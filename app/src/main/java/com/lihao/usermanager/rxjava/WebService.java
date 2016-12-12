package com.lihao.usermanager.rxjava;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lihao on 2016/12/12.
 */

public interface WebService {

    @GET("queryservlet")
    Call<UserBean> getUser(@Query("id") int userId);

    @GET("queryservlet")
    Observable<UserBean> queryUserById(@Query("id") int userId);
}
