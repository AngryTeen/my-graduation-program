package edu.learning.jonnypeng.graduationproject.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import edu.learning.jonnypeng.graduationproject.service.GetInfoService;

public class BootBroadcastReceiver extends BroadcastReceiver {
    public BootBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent getInfoServerceIntent = new Intent(context, GetInfoService.class);
        context.startService(getInfoServerceIntent);
    }
}
