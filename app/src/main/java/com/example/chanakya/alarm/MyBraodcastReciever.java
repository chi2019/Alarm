package com.example.chanakya.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

/**
 * Created by chanakya on 2/8/18.
 */

public class MyBraodcastReciever extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator  = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(10000);
    }
}
