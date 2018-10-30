
import {NativeEventEmitter, NativeModules, Alert} from 'react-native';
const { Pedometer } = NativeModules;

const ACTIVITY_TIMEOUT = 15000;

export class ActivityService {
  lastTimer = null;
  subscription = null;


  constructor(activityStore) {
    this.activityStore = activityStore;
  }

  init() {

    return Pedometer.isSupported().then(isSupported => {

      if (!isSupported) {

        Alert.alert(
          'Sorry',
          'Your device not supporter step counter :(',
          [
            {text: 'OK'},
          ],
          { cancelable: false }
        );
        return false;
      }


      Pedometer.init();

      window.setInterval(() => {
        this.activityStore.changeProgress(-1);
      }, ACTIVITY_TIMEOUT / 100);

      this.subscription = new NativeEventEmitter(Pedometer).addListener(
        Pedometer.ON_ACTIVITY_CHANGED,
        () => {

          this.activityStore.setProgress(100);

          this.activityStore.setIsActive(true);

          if (this.lastTimer) {
            window.clearTimeout(this.lastTimer);
          }

          this.lastTimer = window.setTimeout(() => {
            this.activityStore.setIsActive(false);
            this.activityStore.setProgress(0);
          }, ACTIVITY_TIMEOUT);
        });

    });



  }
}