package com.hereandnow.utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hereandnow.mvvm.R;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by Anbarasan S on 21-06-2021
 */
public class Utils {
    private static Typeface type;

    public static String getConvertedErrorBody(Response response) {
        String value = null;
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            value = jObjError.getString("error");
            Log.e("getConvertedErrorBody ", jObjError.toString());
        } catch (Exception e) {
            e.printStackTrace();
            value = "Internal Server Error";
        }
        return value;
    }

    public static void handleErrorResponse(Throwable throwable, Context context) {
        if (throwable instanceof IOException) {
            showCustomAlert(context, context.getResources().getString(R.string.alert), context.getString(R.string.no_internet_connection), "Login");
        } else {
            showCustomAlert(context, context.getResources().getString(R.string.alert), context.getResources().getString(R.string.something_wrong), "Login");
        }
    }

    /**
     * Custom Alert
     */
    public static void showCustomAlert(final Context context, String title, String message, final String from) {
        final Dialog dialog = new Dialog(context, R.style.custom_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.common_custom_dialog);
        TextView dialog_text = (TextView) dialog.findViewById(R.id.title);
        TextView dialog_message = (TextView) dialog.findViewById(R.id.subtitle);
        TextView dialog_positive = (TextView) dialog.findViewById(R.id.dialogButtonOK);
        TextView dialog_negative = (TextView) dialog.findViewById(R.id.dialogButtonNO);
        LinearLayout parent = (LinearLayout) dialog.findViewById(R.id.dialog_parent_layout);
        View dialog_separator = (View) dialog.findViewById(R.id.separator);
        if (title != null)
            dialog_text.setText(title);
        else {
            dialog_text.setVisibility(View.GONE);
            int paddingDp = 25, paddingBottom = 15;
            float density = context.getResources().getDisplayMetrics().density;
            int paddingPixel = (int) (paddingDp * density);
            int paddingPixel2 = (int) (paddingBottom * density);
            dialog_message.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel2);
        }
        if (!TextUtils.isEmpty(message))
            dialog_message.setText(message);
        else
            dialog_message.setVisibility(View.GONE);
        if (from.equals("failed")) {
            dialog_negative.setVisibility(View.GONE);
            dialog_separator.setVisibility(View.GONE);
            dialog_positive.setText("OK");
            dialog_positive.setOnClickListener(v -> {
                dialog.cancel();
                dialog.dismiss();
            });
        }
        dialog.setCancelable(false);
        dialog.show();
    }
}
