package com.ameer.android.androidMapAmeerCW.tracker;

import android.os.Bundle;

import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;
import com.ameer.android.androidMapAmeerCW.logger.LocationLogger;
import com.ameer.android.androidMapAmeerCW.classes.Location;
import com.ameer.android.androidMapAmeerCW.classes.LoggableLocation;

import java.util.Calendar;

/**
 * Handle location tracking and logging
 */
public class LocationTracking {

  private final static long Log_Time = 1000;
  private final LocationLogger mLocationLogger;
  private final IALocationListener mLocationListener;
  private final IALocationManager mLocationManager;
  private boolean isTracking = false;

  public LocationTracking(LocationLogger newLogger, IALocationManager newLocMan, TrackerCallback cb) {
    mLocationLogger = newLogger;
    mLocationManager = newLocMan;
    mLocationListener = new IALocationListener() {
      @Override
      public void onLocationChanged(IALocation location) {
        Location entry = new LoggableLocation(
            location.getLatitude(),
            location.getLongitude(),
            Calendar.getInstance().getTime().toString());
        mLocationLogger.log(entry);
        cb.execute(entry);
      }
      @Override
      public void onStatusChanged(String s, int i, Bundle bundle) {

      }
    };
  }
  // begin to track location
  public void start() {
    if (!isTracking) {
      mLocationManager.requestLocationUpdates(
        IALocationRequest
          .create()
          .setFastestInterval(Log_Time),
        mLocationListener
      );
      isTracking = true;
    }
  }
  // stop tracking
  public void stop() {
    if (isTracking) {
      mLocationManager.removeLocationUpdates(mLocationListener);
      isTracking = false;
    }
  }
  // clear out references
  public void onDestroy() {
    mLocationLogger.onDestroy();
    mLocationManager.destroy();
  }
  // is tracking set to true or false
  public boolean isTracking() {
    return isTracking;
  }
}
