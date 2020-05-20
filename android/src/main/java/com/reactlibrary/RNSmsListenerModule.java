
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNSmsListenerModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNSmsListenerModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNSmsListener";
  }

    private void registerReceiverIfNecessary(BroadcastReceiver receiver) {
        if (getCurrentActivity() == null) return;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getCurrentActivity().registerReceiver(
                        receiver,
                        new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
                );
            }
            isReceiverRegistered = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void registerReceiver() {
        mReceiver = new OtpReceiver(reactContext);
        registerReceiverIfNecessary(mReceiver);
    }

    @ReactMethod
    public void unregisterReceiver() {
        if (getCurrentActivity() != null && mReceiver != null && isReceiverRegistered) {
            try{
                getCurrentActivity().unregisterReceiver(mReceiver);
                isReceiverRegistered = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}