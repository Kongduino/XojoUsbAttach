package com.github.kongduino.usbattach;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Android starts this activity when a USB device listed in usbattach_device_filter is plugged in (once the user has
 * chosen the app). That choice grants the app the USB permission for the device. The activity only opens the app
 * that contains it, through its launcher intent (or brings it to the front), then closes.
 */
public class UsbAttachedActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent launch = getPackageManager().getLaunchIntentForPackage(getPackageName());
        if (launch != null) {
            launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(launch);
        }
        finish();
    }
}
