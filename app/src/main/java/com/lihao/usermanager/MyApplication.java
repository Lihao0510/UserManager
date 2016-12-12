package com.lihao.usermanager;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by lihao on 2016/12/10.
 */

public class MyApplication extends Application{

    private static RequestQueue mQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        mQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getQueue(){
        return mQueue;
    }
}
