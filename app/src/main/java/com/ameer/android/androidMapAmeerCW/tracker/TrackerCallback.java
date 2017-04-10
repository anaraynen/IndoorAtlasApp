package com.ameer.android.androidMapAmeerCW.tracker;

import com.ameer.android.androidMapAmeerCW.classes.Location;

/**
 * Defines a callback to operate on the provided Location
 */
public interface TrackerCallback {

  /**
   * @param input the Location the callback receives as its parameter
   */
  void execute (Location input);
}
