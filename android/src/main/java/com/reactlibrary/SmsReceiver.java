package com.reactlibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Objects;

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";
    private ReactApplicationContext mContext;

    public SmsReceiver() {
        super();
    }

    public SmsReceiver(ReactApplicationContext context) {
        mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Objects.equals(intent.getAction(), "android.provider.Telephony.SMS_RECEIVED")) {
                Log.d("TAG", "onReceive: " + intent);
                String smsSender = "";
                String smsBody = "";

                Bundle smsBundle = intent.getExtras();
                if (smsBundle != null) {
                    Object[] pdus = (Object[]) smsBundle.get("pdus");
                    if (pdus == null) {
                        // Display some error to the user
                        Log.e(TAG, "SmsBundle had no pdus key");
                        return;
                    }
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < messages.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        smsBody += messages[i].getMessageBody();
                    }
                    smsSender = messages[0].getOriginatingAddress();
                }

                Log.d(TAG, "onReceive: " + smsSender + " " + smsBody);
                WritableNativeMap receivedMessage = new WritableNativeMap();
                receivedMessage.putString("sender", smsSender);
                receivedMessage.putString("body", smsBody);
                Log.d("Tag", "onReceive: " + receivedMessage);
                if (mContext != null) {
                    mContext
                            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit("onReceiveMessage", receivedMessage);

                }
            }
        }
    }
}
