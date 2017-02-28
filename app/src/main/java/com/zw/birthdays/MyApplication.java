package com.zw.birthdays;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by lenovo on 2017/2/28.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
