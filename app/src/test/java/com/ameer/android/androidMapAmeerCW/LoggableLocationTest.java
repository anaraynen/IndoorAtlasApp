package com.ameer.android.androidMapAmeerCW;

import com.ameer.android.androidMapAmeerCW.classes.Location;
import com.ameer.android.androidMapAmeerCW.classes.LoggableLocation;
import java.util.Calendar;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * LoggableLocation tests
 */
public class LoggableLocationTest {

  private static final Double LAT = 0.54676;
  private static final Double LONG = -1.24545;
  private static final String TIMESTAMP = Calendar.getInstance().toString();
  private static final String EXPECTED_STRING =
    "Longitude: " + LONG + "\nLatitude: " + LAT + "\nTimestamp: " + TIMESTAMP;

  /**
   * Testing toString() method
   */
  @Test
  public void it_should_return_expected_string_if_toString_is_called() {
    Location entry = new LoggableLocation(LAT, LONG, TIMESTAMP);
    assertEquals(EXPECTED_STRING, entry.toString());
  }

}
