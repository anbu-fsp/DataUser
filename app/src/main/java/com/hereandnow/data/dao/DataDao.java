package com.hereandnow.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hereandnow.data.model.DataDaoModel;

import java.util.List;

/**
 * Created by Anbarasan S on 25-11-2022
 */
@Dao
public interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDataDetails(DataDaoModel dataDaoModel);

    @Query("SELECT * FROM DataDaoModel")
    List<DataDaoModel> getUserDetails();

    @Query("UPDATE DataDaoModel SET comment= :comment WHERE id = :id")
    void updateData(String comment, int id);
}
