package com.ameer.android.androidMapAmeerCW.classes;

import android.support.annotation.NonNull;

//attempt to log location every second to the stated db using string values from tracking user location
final public class LoggableLocation implements Location {
  private final Double longitude;
  private final Double latitude;
  private final String timestamp;

  public LoggableLocation(@NonNull Double lat,
                          @NonNull Double lon,
                          @NonNull String ts) {
    longitude = lon;
    latitude = lat;
    timestamp = ts;
  }

  @Override
  public Double getLongitude() {
    return longitude;
  }

  @Override
  public Double getLatitude() {
    return latitude;
  }

  public String getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return "Longitude: " + longitude +
        "\nLatitude: " + latitude +
        "\nTimestamp: " + timestamp;
  }
}
