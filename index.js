import {
  NativeModules,
  DeviceEventEmitter,
  Platform,
  PermissionsAndroid,
} from 'react-native';

const isAndroid = Platform.OS === 'android';

const { RNSmsListener } = NativeModules;

const requestPermissions = () =>
  isAndroid &&
  PermissionsAndroid.requestMultiple([
    'android.permission.READ_SMS',
    'android.permission.RECEIVE_MMS',
  ]);

export default {
  requestPermissions,
  registerReceiver: () => {
    if (isAndroid) {
      RNSmsListener.registerReceiver();
    }
  },
  unregisterReceiver: () => {
    if (isAndroid) {
      RNSmsListener.unregisterReceiver();
    }
  },
  onSMS: isAndroid
    ? (listener) => {
        DeviceEventEmitter.addListener('onReceiveMessage', listener);
      }
    : null,
};
