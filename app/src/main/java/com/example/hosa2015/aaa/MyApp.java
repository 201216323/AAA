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
        PlatformConfig.setWeixin("wx1e48313855ee1630","4d395bee2cc7ce077773e0cc9d93da97");
        PlatformConfig.setQQZone("1105010761","QWCN9CxD0blbth4M");
        PlatformConfig.setSinaWeibo("1802731919","1780dd641fba0d3656a071c4e74ed840");
    }
}
