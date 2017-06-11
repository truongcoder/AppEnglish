package com.example.pvtruong.appenglishlock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by PVTruong on 05/04/2017.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences pre = PreferenceManager.getDefaultSharedPreferences(context);
        if(pre.getBoolean("status",false)==true){
            intent = new Intent(context, EnglockService.class);
            context. startService(intent);
        }

    }
}
