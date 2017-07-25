package com.example.sameer.sharemylocation;

/**
 * Created by sameer on 6/7/2017.
 */
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by sameer on 5/31/2017.
 */
public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
