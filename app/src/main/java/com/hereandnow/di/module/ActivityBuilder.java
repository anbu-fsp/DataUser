package com.hereandnow.di.module;

import com.hereandnow.base.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Anbarasan S on 24-11-2022
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

}
