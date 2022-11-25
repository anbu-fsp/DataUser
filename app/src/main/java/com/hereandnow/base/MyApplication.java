package com.hereandnow.base;

import android.app.Activity;
import android.app.Application;
import com.hereandnow.di.component.ApplicationComponent;
import com.hereandnow.di.component.DaggerApplicationComponent;
import com.hereandnow.di.module.ApplicationModule;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by Anbarasan S on 14-10-2021
 */
public
class MyApplication extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationComponent appComponent = DaggerApplicationComponent.builder()
                .application(this)
                .applicationModule(new ApplicationModule(this))
                .build();
        appComponent.inject(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
