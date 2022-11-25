package com.hereandnow.mvvm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.hereandnow.data.model.DataDaoModel;
import com.hereandnow.mvvm.R;
import com.hereandnow.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anbarasan S on 24-11-2022
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.SettingsViewHolder> {
    private final Context mContext;
    private final NavController navController;
    List<DataDaoModel> DataDetailsModelArrayList = new ArrayList<>();

    public DataAdapter(Context context, NavController navController, List<DataDaoModel> DataDetailsModelArrayList) {
        mContext = context;
        this.navController = navController;
        this.DataDetailsModelArrayList = DataDetailsModelArrayList;
    }

    @NonNull
    @Override
    public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_row_item, parent, false);
        return new SettingsViewHolder(view);
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull SettingsViewHolder holder, int position) {

        DataDaoModel dataDetailsModel = DataDetailsModelArrayList.get(position);
        holder.first_name_value_tview.setText(dataDetailsModel.getFirst_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.ID, dataDetailsModel.getId());
                bundle.putString(Constant.FIRST_NAME, dataDetailsModel.getFirst_name());
                bundle.putString(Constant.LAST_NAME, dataDetailsModel.getLast_name());
                bundle.putString(Constant.EMAIL, dataDetailsModel.getEmail());
                bundle.putString(Constant.COMMENT, dataDetailsModel.getComment());
                navController.navigate(R.id.action_loginFragment_to_signupFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return DataDetailsModelArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    static class SettingsViewHolder extends RecyclerView.ViewHolder {

        TextView first_name_value_tview;

        SettingsViewHolder(@NonNull View itemView) {
            super(itemView);
            first_name_value_tview = itemView.findViewById(R.id.first_name_value_tview);
        }
    }
}