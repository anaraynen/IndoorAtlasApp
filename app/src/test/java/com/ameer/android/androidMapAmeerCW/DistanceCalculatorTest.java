package com.ameer.android.androidMapAmeerCW;

import com.ameer.android.androidMapAmeerCW.utils.DistanceCalculator;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Since we don't have a device running, Android's Location
 * class just returns a default value of 0.0f
 */
public class DistanceCalculatorTest {
  private static final Double LAT_A = 51.750839;
  private static final Double LONG_A = -1.257935;
  private static final Double LAT_B = 51.512161;
  private static final Double LONG_B = -0.115356;

  private static final Float EXPECTED = 0.0f;

  /**
   * Android's Location code is tested, this test just checks that
   * the function doesn't throw some crazy error
   */
  @Test
  public void it_should_return_distance_between_two_sets_of_coordinates() {
    assertEquals(EXPECTED, DistanceCalculator.getDistanceBetween(LAT_A, LONG_A, LAT_B, LONG_B));
  }

  @Test
  public void it_should_return_distance_between_default_values() {
    assertEquals(EXPECTED, DistanceCalculator.getDistanceBetween(0.0, 0.0, 0.0, 0.0));
  }

  @Test(expected =  NullPointerException.class)
  public void it_should_throw_if_null_is_provided() {
    DistanceCalculator.getDistanceBetween(null, null, null, null);
  }
}
