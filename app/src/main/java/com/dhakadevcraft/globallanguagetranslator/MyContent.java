package com.dhakadevcraft.globallanguagetranslator;

import android.app.Application;

import com.facebook.ads.AudienceNetworkAds;

public class MyContent extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AudienceNetworkAds.initialize(this);
    }
}
