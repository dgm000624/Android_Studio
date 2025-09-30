package com.iot.application;

import android.app.Application;

public class MyApplication extends Application {

    private String globalString;
    public MyApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        globalString = "Hello Android";
    }

    public String getGlobalString()
    {
        return this.globalString;
    }

    public void setGlobalString(String globalString){
        this.globalString = globalString;
    }

}
