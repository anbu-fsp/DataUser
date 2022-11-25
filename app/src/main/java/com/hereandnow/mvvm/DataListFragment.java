package com.hereandnow.mvvm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hereandnow.data.dao.DataDao;
import com.hereandnow.data.model.DataDaoModel;
import com.hereandnow.data.model.response.DataResponseModel;
import com.hereandnow.data.repository.DataListRepository;
import com.hereandnow.mvvm.adapter.DataAdapter;
import com.hereandnow.mvvm.databinding.FragmentLoginBinding;
import com.hereandnow.support.CustomProgressDialog;
import com.hereandnow.utils.ApiResponse;
import com.hereandnow.utils.Utils;
import com.hereandnow.viewModel.DataViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.android.support.AndroidSupportInjection;

import javax.inject.Inject;

public class DataListFragment extends Fragment {

    private FragmentLoginBinding fragmentLoginBinding;

    private View view;

    @Inject
    DataListRepository dataListRepository;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private Context mContext;

    private DataViewModel dataViewModel;

    private CustomProgressDialog customProgressDialog;

    private DataAdapter dataAdapter;

    private ArrayList<DataDaoModel> dataDetailsList;

    NavController navController;

    @Inject
    DataDao dataDao;

    public DataListFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        configureDagger();
        configureViewModel();
        mContext = context;
    }

    private void configureViewModel() {
        dataViewModel = new ViewModelProvider(this, viewModelFactory).get(DataViewModel.class);
    }

    /**
     * Dagger Configuration
     */
    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onViewCreated(@NonNull @org.jetbrains.annotations.NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        view = fragmentLoginBinding.getRoot();

        initUI();

        return view;
    }

    private void initUI() {
        dataDetailsList = new ArrayList<>();
        setRecyclerViewAdapter(dataDetailsList);

        getListItems();
    }

    private void setRecyclerViewAdapter(List<DataDaoModel> list) {
        NavController navController = Navigation.findNavController((Activity) mContext, R.id.nav_host_fragment);
        dataAdapter = new DataAdapter(mContext, navController, list);
        fragmentLoginBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        fragmentLoginBinding.recyclerView.setHasFixedSize(true);
        fragmentLoginBinding.recyclerView.setNestedScrollingEnabled(false);
        fragmentLoginBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentLoginBinding.recyclerView.setAdapter(dataAdapter);
    }

    private void getListItems() {
        List<DataDaoModel> dataDaoModels = new ArrayList<>();
        dataDaoModels = dataDao.getUserDetails();
        if (dataDaoModels!=null && dataDaoModels.size() > 0) {
            dataDetailsList.addAll(dataDaoModels);
            Toast.makeText(mContext, "Data in local db", Toast.LENGTH_SHORT).show();
        } else {
            new Handler().post(() -> dataViewModel.getDataDetails().observe((LifecycleOwner) mContext, this::dataResponse));
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void dataResponse(ApiResponse<DataResponseModel> dataResponseModelApiResponse) {

        DataResponseModel dataResponseModel = dataResponseModelApiResponse.getData();
        switch (dataResponseModelApiResponse.getStatus()) {
            case LOADING:
                break;
            case SUCCESS:
                assert dataResponseModel != null;
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();

                if (dataResponseModel.getData().size() > 0) {

                    for (int i = 0; i < dataResponseModel.getData().size(); i++) {

                        //save data in local db
                        DataDaoModel dataDetailsModel = new DataDaoModel();
                        dataDetailsModel.setId(dataResponseModel.getData().get(i).getId());
                        dataDetailsModel.setFirst_name(dataResponseModel.getData().get(i).getFirst_name());
                        dataDetailsModel.setLast_name(dataResponseModel.getData().get(i).getLast_name());
                        dataDetailsModel.setEmail(dataResponseModel.getData().get(i).getEmail());
                        dataDetailsModel.setAvatar(dataResponseModel.getData().get(i).getAvatar());
                        dataDetailsModel.setComment("");

                        //insert data in local db
                        dataDao.insertDataDetails(dataDetailsModel);

                        dataDetailsList.add(dataDetailsModel);
                    }
                }

                dataAdapter.notifyDataSetChanged();

                break;
            case FAILURE:
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
                Utils.showCustomAlert(mContext, getString(R.string.alert),
                        Utils.getConvertedErrorBody(dataResponseModelApiResponse.getFailureResponse()), "failed");
                break;
            case ERROR:
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
                Utils.handleErrorResponse(dataResponseModelApiResponse.getError(), mContext);
                break;
        }
    }
}
