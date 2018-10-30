import React, {Component} from "react";
import {Image, StyleSheet, View, Dimensions} from 'react-native';
import ProgressBarAnimated from 'react-native-progress-bar-animated';
import {observer} from 'mobx-react/native'

@observer
export class Happy extends Component {
  render() {

    const barWidth = Dimensions.get('screen').width - 30;
    const store = this.props.store;
    const imgSource = store.isActive
      ? require('../../img/happy.png')
      : require('../../img/sad.png');

    return (
      <View style={styles.container}>
        <Image style={styles.image} source={imgSource} />

        <ProgressBarAnimated
          width={barWidth}
          value={store.progress}
          barAnimationDuration={50}
          backgroundColorOnComplete="#6CC644"
        />

      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
    marginBottom: 40
  },
  image: {
    flex:1,
    alignSelf: 'stretch',
    resizeMode: 'contain',
    height: undefined,
    width: undefined
  }
});