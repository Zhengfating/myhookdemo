package com.example.administrator.myhookdemo;

import android.app.Application;
import android.content.Context;
import com.example.administrator.myhookdemo.hookpms.ServiceManagerWraper;

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
//        ServiceManagerWraper.hookPMS(base);
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
