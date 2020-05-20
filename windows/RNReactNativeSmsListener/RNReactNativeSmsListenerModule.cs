using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace React.Native.Sms.Listener.RNReactNativeSmsListener
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNReactNativeSmsListenerModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNReactNativeSmsListenerModule"/>.
        /// </summary>
        internal RNReactNativeSmsListenerModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNReactNativeSmsListener";
            }
        }
    }
}
