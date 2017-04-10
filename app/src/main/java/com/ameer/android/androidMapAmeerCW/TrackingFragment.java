package com.ameer.android.androidMapAmeerCW;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.indooratlas.android.sdk.IALocationManager;
import com.ameer.android.androidMapAmeerCW.logger.FireBaseLocationLogger;
import com.ameer.android.androidMapAmeerCW.classes.LocationOfInterest;
import com.ameer.android.androidMapAmeerCW.classes.Location;
import com.ameer.android.androidMapAmeerCW.tracker.LocationTracking;
import com.ameer.android.androidMapAmeerCW.utils.DistanceCalculator;

public class TrackingFragment extends Fragment {
  private static final String TAG = "TrackingLocation";
  private static final String dbFirebase = "locations";
  private static final double LocationOfInterestLat = 51.521975;
  private static final double LocationOfInterestLong = -0.130462;

  private LocationTracking mTracker;
  private TextView currentLocation;
  private DatabaseReference mDatabase;

  public static Fragment newInstance() { return new TrackingFragment(); }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
                           ViewGroup container,
                           Bundle savedInstanceState) {

    View v = inflater.inflate(R.layout.tracking_fragment, container, false);

    Location interestingLocation = new LocationOfInterest(
      LocationOfInterestLong,
      LocationOfInterestLat,
      "Location: BirkBeckUni");
    currentLocation = (TextView) v.findViewById(R.id.current_loc);
    // get instance of Firebase db ready using supplied ref ready for logging location
    mTracker = new LocationTracking(
      FireBaseLocationLogger.getInstance(FirebaseDatabase.getInstance().getReference(dbFirebase)),

      IALocationManager.create(super.getActivity()),
      s -> {
        // display current location as string
        currentLocation.setText(s.toString());
        Log.d(TAG, s.toString());

        // calculate the distance from the specified location
        Float distance = DistanceCalculator.getDistanceBetween(
          s.getLatitude(),
          s.getLongitude(),
          interestingLocation.getLatitude(),
          interestingLocation.getLongitude()
        );

        // show a message if within 3m
        if (distance <= 3) {
          String message = "You are " + distance + " from " +
            ((LocationOfInterest) interestingLocation).getName();
          Toast
            .makeText(this.getContext(), message, Toast.LENGTH_SHORT)
            .show();
        }
        else {
          Toast
            .makeText(this.getContext(), "You are not close yet, keep going.", Toast.LENGTH_SHORT)
            .show();
        }
      }
    );

    // enable the start button
    v.findViewById(R.id.start_loc_log).setEnabled(true);
    // initialise button listeners
    initButtonListeners((Button) v.findViewById(R.id.start_loc_log),
                (Button) v.findViewById(R.id.stop_loc_log));

    return v;
  }

  @Override
  public void onPause() {
    // if (mTracker.isTracking())
    mTracker.stop();
    super.onPause();
  }

  @Override
  public void onDestroy() {
    mTracker.onDestroy();
    super.onDestroy();
  }

  // use two buttons to start and stop all location tracking and logging
  private void initButtonListeners(Button start, Button stop) {
    start.setOnClickListener(l -> {
      if (hasInternetConnection(this.getActivity())) {
        mTracker.start();
        start.setEnabled(false);
        stop.setEnabled(true);
      }
      else {
        noInternetMessage();
      }
    });
    stop.setOnClickListener(l -> {
      if (hasInternetConnection(this.getActivity())) {
        mTracker.stop();
        stop.setEnabled(false);
        start.setEnabled(true);
      }
      else {
        noInternetMessage();
      }
    });
  }

  //verify that an internet connection is available
  protected boolean hasInternetConnection(Context context)
  {
    boolean isConnected = false;
    //if connectivity object is available
    ConnectivityManager con_manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (con_manager.getActiveNetworkInfo() != null)
    {
      //if network is available
      if (con_manager.getActiveNetworkInfo().isAvailable())
      {
        //if connected
        if (con_manager.getActiveNetworkInfo().isConnected())
        {
          //yep... there is connectivity
          isConnected = true;
        }
      }
    }
    return isConnected;
  }

  //show something if there is no internet connectivity
  protected void noInternetMessage()
  {
    //build an alert box
    AlertDialog.Builder alertbox = new AlertDialog.Builder(this.getActivity());
    alertbox.setIcon(android.R.drawable.ic_dialog_alert);
    // Set the message to display
    alertbox.setMessage("Error in network connection. Please check and try again.");
    // Add a neutral button to the alert box and assign a click listener
    alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener()
    {
      @Override
      public void onClick(DialogInterface mainWebView, int arg1)
      {
        //quit the app
        //finish();
        //stay on main activity and dont close the app
        //startActivity();
      }
    });
    // show the alert box
    alertbox.show();
  }
}
