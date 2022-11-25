package com.hereandnow.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.hereandnow.viewModel.DataViewModel;
import com.hereandnow.viewModel.factory.AllViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Anbarasan S on 24-11-2022
 */
@Module
public abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(AllViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(DataViewModel.class)
    abstract ViewModel provideHistoryViewModel(DataViewModel historyViewModel);


}
