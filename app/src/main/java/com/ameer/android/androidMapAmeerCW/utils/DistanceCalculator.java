package com.ameer.android.androidMapAmeerCW.utils;

import android.location.Location;
import android.support.annotation.NonNull;

/**
 * Utility class to handle distance calculations
 */
public final class DistanceCalculator {
  private DistanceCalculator(){}

  @NonNull
  public static Float getDistanceBetween(@NonNull Double latA,
                                         @NonNull Double longA,
                                         @NonNull Double latB,
                                         @NonNull Double longB) {
    Location locA = new Location("Location A");
    Location locB = new Location("Location B");
    locA.setLatitude(latA);
    locA.setLongitude(longA);
    locB.setLatitude(latB);
    locB.setLongitude(longB);
    return locA.distanceTo(locB);
  }
}
