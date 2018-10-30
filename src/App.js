/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {StyleSheet,  View} from 'react-native';
import {Happy} from './components/Happy'
import activityStore from './stores/activityStore';
import {ActivityService} from './services/ActivityService'


type Props = {};
export default class App extends Component<Props> {

  activityService = new ActivityService(activityStore);

  componentDidMount() {
    this.activityService.init();
  }


  render() {
    return (
      <View style={styles.container}>
        <Happy store={ activityStore } />
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
  },
});
