import {observable} from 'mobx'


class ActivityStore {
  @observable isActive = false;
  @observable progress = 0;


  setIsActive (isActive) {
    this.isActive = isActive;
  }

  setProgress(progress) {
    this.progress = progress;
  }

  changeProgress(delta) {
    this.progress += delta;
    if (this.progress < 0) {
      this.progress = 0;
    }
  }
}


const activityStore = new ActivityStore();
export default activityStore