package com.hereandnow.di.module;

import static com.hereandnow.utils.Constant.CURRENT_DB_NAME;

import android.app.Application;

import androidx.room.Room;

import com.hereandnow.data.dao.DataDao;
import com.hereandnow.data.database.LocalDataBase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {
    private LocalDataBase localDatabase;

    @Provides
    @Singleton
    LocalDataBase provideLocalDatabase(Application application) {
        return this.localDatabase = Room.databaseBuilder(application,
                LocalDataBase.class, CURRENT_DB_NAME).allowMainThreadQueries().build();
    }

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    DataDao provideLoginDao(LocalDataBase localDataBase) {
        return localDatabase.loginDao();
    }
}
