package com.hereandnow.retrofit;

import com.hereandnow.data.model.response.DataResponseModel;
import com.hereandnow.utils.Constant;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataWebservice {

    @GET(Constant.DATA_END_POINTS)
    Call<DataResponseModel> getData ();
}
