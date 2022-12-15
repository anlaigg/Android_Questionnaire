package com.farrirs.Questionaire2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {
    private static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("接收广播", "onReceive: ");
        Log.e("接收广播", "onReceive: " + intent.getAction());
        //开机启动
        if (ACTION.equals(intent.getAction())) {
            Log.e("接收广播", "onReceive: 启动了。。。");
        }
        if (ACTION.equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent mainIntent = new Intent(context, Login.class);
//            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//为待启动的Activity指定FLAG_ACTIVITY_NEW_TASK标记位。
            context.startActivity(mainIntent);


//            Intent intentte = new Intent(context,Login.class);
//            intentte.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intentte);

            Log.e("接收广播", "onReceive: " + mainIntent);
            Log.e("接收广播", "ccc");

        }

    }
}
