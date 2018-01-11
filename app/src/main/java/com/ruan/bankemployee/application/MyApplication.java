package com.ruan.bankemployee.application;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  by ruan on 2018/1/7.
 * 全局类
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    public static List<String> windows = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        getInstance();
    }
    public static MyApplication getInstance(){
        if (instance == null){
            instance = new MyApplication();
        }
        return instance;
    }
    public static void exit(){
        windows.clear();
    }
}
