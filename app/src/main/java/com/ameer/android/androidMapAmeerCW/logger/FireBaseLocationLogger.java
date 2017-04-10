package com.ameer.android.androidMapAmeerCW.logger;

import android.support.annotation.NonNull;
import com.google.firebase.database.DatabaseReference;
import com.ameer.android.androidMapAmeerCW.classes.Location;

//Using objects, we can log a location to the Firebase db with the supplied info
public class FireBaseLocationLogger implements LocationLogger {
  private static FireBaseLocationLogger _instance = null;
  private static DatabaseReference dr = null;

  private FireBaseLocationLogger() {}
  //here use the supplied db ref name to check and create and return the relevant db instance ready for logging
  public static FireBaseLocationLogger getInstance(DatabaseReference dbRef) {
    if (dr == null) dr = dbRef;
    if (_instance == null) _instance = new FireBaseLocationLogger();
    return _instance;
  }

  @Override
  public String log(@NonNull Location entry) {
    String newId = dr.push().getKey();
    dr.child(newId).setValue(entry);
    return newId;
  }

  @Override
  public void onDestroy() {
    dr = null;
    _instance = null;
  }
}
