package com.dhakadevcraft.globallanguagetranslator;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class NoInternetDialog extends AlertDialog {

    private MainActivity mActivity;

    public NoInternetDialog(Context context, MainActivity activity) {
        super(context);
        mActivity = activity;
    }

    public static NoInternetDialog create(Context context, MainActivity activity) {
        NoInternetDialog dialog = new NoInternetDialog(context, activity);
        dialog.setCancelable(false);
        dialog.setView(LayoutInflater.from(context).inflate(R.layout.custom_no_internet_dialog, null));

        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button okButton = findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mActivity != null) {
                    mActivity.finishApp();
                }
            }
        });
    }
}