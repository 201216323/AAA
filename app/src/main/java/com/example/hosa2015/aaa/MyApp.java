package com.example.hosa2015.aaa;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;

/**
 * Created by hosa2015 on 2016/3/9.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PlatformConfig.setWeixin("wx6fa74cf71fb1c489","d4624c36b6795d1d99dcf0547af5443d");
        PlatformConfig.setQQZone("1105010761","QWCN9CxD0blbth4M");
        PlatformConfig.setSinaWeibo("1802731919","1780dd641fba0d3656a071c4e74ed840");
    }
}
