package com.ameer.android.androidMapAmeerCW.logger;

import android.support.annotation.NonNull;
import com.ameer.android.androidMapAmeerCW.classes.Location;

public interface LocationLogger {

// location as object to string ready to be logged
  String log(@NonNull Location entry);

// clear out references and instances
  void onDestroy();
}
