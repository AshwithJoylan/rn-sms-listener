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

const check = async () => new Promise((resolve, reject) => {
  try {
    const g1 = await PermissionsAndroid.check('android.permission.READ_SMS');
    const g2 = await PermissionsAndroid.check('android.permission.RECEIVE_SMS');
    if (g1 && g2) {
      resolve(true);
    } else {
      reject(false);
    }
  } catch (err) {
  reject(false)
 }
})
  
export default {
  requestPermissions,
  registerReceiver: async () => {
    if (isAndroid) {
      const granted = await check();
      if (granted) {
        RNSmsListener.registerReceiver();
      } else {
        console.log('RN SMS LISTENER: -> Permission Not Granted');
      }
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
