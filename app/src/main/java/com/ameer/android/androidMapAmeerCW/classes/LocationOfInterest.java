package com.ameer.android.androidMapAmeerCW.classes;

import android.support.annotation.NonNull;

public final class LocationOfInterest implements Location {
  private final Double longitude;
  private final Double latitude;
  private final String name;

  public LocationOfInterest(@NonNull Double strLon,
                            @NonNull Double strLat,
                            @NonNull String strName) {
    longitude = strLon;
    latitude = strLat;
    name = strName;
  }

  @Override
  public Double getLongitude() {
    return longitude;
  }

  @Override
  public Double getLatitude() {
    return latitude;
  }

  public String getName() {
    return name;
  }
}
