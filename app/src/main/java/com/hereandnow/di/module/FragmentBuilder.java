package com.hereandnow.di.module;

import com.hereandnow.mvvm.DataListFragment;
import com.hereandnow.mvvm.DatailsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Anbarasan S on 24-11-2022
 */
@Module
public abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract DataListFragment contributeLoginFragment();

    @ContributesAndroidInjector
    abstract DatailsFragment contributeSignupFragment();

}
