package com.xenderatestapp.modules;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.facebook.react.common.ReactConstants.TAG;


public class PedometerModule extends ReactContextBaseJavaModule implements SensorEventListener, GoogleApiClient.ConnectionCallbacks, OnDataPointListener, GoogleApiClient.OnConnectionFailedListener {

  private static final String ON_ACTIVITY_CHANGED_KEY = "ON_ACTIVITY_CHANGED";
  private static final String ON_ACTIVITY_CHANGED = "OnActivityChanged";

  GoogleApiClient client;

  public PedometerModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "Pedometer";
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put(ON_ACTIVITY_CHANGED_KEY, ON_ACTIVITY_CHANGED);
    return constants;
  }


  @ReactMethod
  public void init() {

    final Activity activity = getCurrentActivity();



    /*GoogleSignInAccount accout = GoogleSignIn.getLastSignedInAccount(activity.getApplicationContext());

    Fitness.getSensorsClient(activity.getApplicationContext(), accout)
            .findDataSources(
                    new DataSourcesRequest.Builder()
                            .setDataTypes(DataType.TYPE_STEP_COUNT_DELTA)
                            .setDataSourceTypes(DataSource.TYPE_RAW)
                            .build())
            .addOnSuccessListener(
                    new OnSuccessListener<List<DataSource>>() {
                      @Override
                      public void onSuccess(List<DataSource> dataSources) {
                        for (DataSource dataSource : dataSources) {
                          Log.i(TAG, "Data source found: " + dataSource.toString());
                          Log.i(TAG, "Data Source type: " + dataSource.getDataType().getName());

                          // Let's register a listener to receive Activity data!
                          if (dataSource.getDataType().equals(DataType.TYPE_LOCATION_SAMPLE)
                                  && mListener == null) {

                            registerFitnessDataListener(dataSource, DataType.TYPE_LOCATION_SAMPLE);
                          }
                        }
                      }
                    })
            .addOnFailureListener(
                    new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "failed", e);
                      }
                    });*/



    final SensorManager manager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);

    final Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
    if (sensor == null) {
      Log.e("PedometerModule", "Failed to acquire STEP_DETECTOR sensor");
      return;
    }
    manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
  }

  @Override
  public void onConnected(@Nullable Bundle var1) {

  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult var1) {

  }

  @Override
  public void onDataPoint(DataPoint dataPoint) {
    for (Field field : dataPoint.getDataType().getFields()) {
      Value val = dataPoint.getValue(field);
      Log.i(TAG, "Detected DataPoint field: " + field.getName());
      Log.i(TAG, "Detected DataPoint value: " + val);
      final int value = val.asInt();
    }
  }

  @Override
  public void onConnectionSuspended(int var1) {
  }


  @ReactMethod
  public void isSupported( final Promise promise ) {
    boolean isSupported = getCurrentActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR);
    promise.resolve(isSupported);
  }

  @ReactMethod
  public void send(String message) {

    getReactApplicationContext()
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(ON_ACTIVITY_CHANGED, message);

  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    final int steps = (int) event.values[0];

    getReactApplicationContext()
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(ON_ACTIVITY_CHANGED, null);

  }

}
