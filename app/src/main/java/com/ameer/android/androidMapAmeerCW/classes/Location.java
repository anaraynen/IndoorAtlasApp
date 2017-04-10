package com.ameer.android.androidMapAmeerCW.classes;

import java.io.Serializable;

//Set a Location with a latitude and longitude
public interface Location extends Serializable {
  Double getLongitude();
  Double getLatitude();
}
