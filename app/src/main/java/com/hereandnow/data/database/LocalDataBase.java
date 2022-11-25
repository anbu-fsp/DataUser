package com.hereandnow.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.hereandnow.data.dao.DataDao;
import com.hereandnow.data.model.DataDaoModel;

@Database(entities = {DataDaoModel.class}, version = 1, exportSchema = false)

public abstract class LocalDataBase extends RoomDatabase {

    public abstract DataDao loginDao();
}
