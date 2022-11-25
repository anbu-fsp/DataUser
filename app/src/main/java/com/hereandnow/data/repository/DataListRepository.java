package com.hereandnow.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.hereandnow.retrofit.DataWebservice;
import com.hereandnow.data.model.response.DataResponseModel;
import com.hereandnow.support.CustomLiveData;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataListRepository {

    private final String TAG = getClass().getSimpleName();
    private final DataWebservice dataDao;

    @Inject
    public DataListRepository(DataWebservice dataDao) {
        this.dataDao = dataDao;
    }

    public CustomLiveData<DataResponseModel> callDataListApi() {
        CustomLiveData<DataResponseModel> dataResponseModelCustomLiveData = new CustomLiveData<>();
        dataDao.getData().enqueue(new Callback<DataResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<DataResponseModel> call, @NonNull Response<DataResponseModel> response) {
                Log.e(TAG,"DataListRepository JSON ->"+new Gson().toJson(response.body()));
                Log.e(TAG,"DataListRepository JSON URL->"+response.raw().request().url()+" Code->"+response.code());
                if (response.code() == 200) {
                    dataResponseModelCustomLiveData.postSuccess(response.body());
                } else {
                    dataResponseModelCustomLiveData.postFailure(response);
                }
            }

            @Override
            public void onFailure(Call<DataResponseModel> call, Throwable t) {
                Log.e(TAG,"DataTask Failure ->"+t.getMessage());
                dataResponseModelCustomLiveData.postError(t);
                call.cancel();
            }
        });
        return dataResponseModelCustomLiveData;
    }
}