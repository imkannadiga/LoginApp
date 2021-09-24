package com.example.littlehelp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;

class loading extends AppCompatActivity{

    Activity activity;
    AlertDialog dialog;

    loading(Activity myactivity) {
        activity = myactivity;
    }

    void startLoadingDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_loading, null));
        dialog = builder.create();
        dialog.show();

    }

    void stopLoading() {
        dialog.dismiss();
    }
}