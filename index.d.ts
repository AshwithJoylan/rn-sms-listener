declare const RNSmsListener: {
  registerReceiver: () => Promise<void>;
  requestPermissions: () => Promise<{
    "android.permission.READ_SMS": PermissionStatus;
    "android.permission.RECEIVE_MMS": PermissionStatus;
  }>;
  unregisterReceiver: () => void;
  onSMS: (
    listener: (message: { sender: string; body: string }) => void
  ) => void;
};

export default RNSmsListener;