package com.jibres.android.utility;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;

import com.jibres.android.R;

public class Dialog {
    public Dialog(final Activity activity, String title, String desc, String btnTitle, boolean Cancelable, final Intent intent) {
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
        /*Title*/
        builderSingle.setTitle(title);
        /*Message*/
        builderSingle.setMessage(desc);
        /*Button*/
        builderSingle.setPositiveButton(btnTitle,
                /*Open Url*/
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (intent == null){
                            dialog.dismiss();
                            activity.finish();
                        }else {
                            dialog.dismiss();
                            activity.finish();
                            activity.startActivity(activity.getIntent());
                        }

                    }
                });

        builderSingle.setNeutralButton(activity.getString(R.string.later), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finish();

            }
        });
        builderSingle.setCancelable(Cancelable);
        Log.e("amingoli78", "Dialog: "+activity.isFinishing() );
        if (!activity.isFinishing()){
            builderSingle.show();
        }

    }
}
