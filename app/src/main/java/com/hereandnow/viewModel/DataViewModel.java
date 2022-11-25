package com.hereandnow.viewModel;

import androidx.lifecycle.ViewModel;

import com.hereandnow.data.model.response.DataResponseModel;
import com.hereandnow.data.repository.DataListRepository;
import com.hereandnow.support.CustomLiveData;

import javax.inject.Inject;

/**
 * Created by Anbarasan S on 22-01-2020
 */
public class DataViewModel extends ViewModel {

    private CustomLiveData<DataResponseModel> dataResponseModelCustomLiveData;
    private DataListRepository dataListRepository;

    @Inject
    public DataViewModel(DataListRepository dataListRepository) {
        this.dataListRepository = dataListRepository;
    }

    public CustomLiveData<DataResponseModel> getDataDetails() {
        return dataResponseModelCustomLiveData = dataListRepository.callDataListApi();
    }
}