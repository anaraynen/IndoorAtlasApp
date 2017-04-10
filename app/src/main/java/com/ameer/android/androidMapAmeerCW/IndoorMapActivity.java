package com.ameer.android.androidMapAmeerCW;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.ameer.android.androidMapAmeerCW.utils.SingleFragmentActivity;
//main activity - code resused from the following:
//https://github.com/IndoorAtlas
public class IndoorMapActivity extends SingleFragmentActivity {

  private static final String TAG = "IndoorMap";
  private static final int CODE_COARSE_LOCATION_PERM = 99;

  @Override
  protected Fragment createFragment() {
    return TrackingFragment.newInstance();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fragment);
    if (hasInternetConnection(this)) {
      checkPermissions();
    }
    else {
      noInternetMessage();
    }
  }

  private void checkPermissions() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
      PackageManager.PERMISSION_GRANTED) {

      Log.d(TAG, "App doesn't have access to COARSE_LOCATION");

      // do we need to give an explanation as to why we need permission?
      if (ActivityCompat.shouldShowRequestPermissionRationale(this,
        Manifest.permission.ACCESS_COARSE_LOCATION)) {

        // show a dialog explaining why we need access to Coarse Location
        new AlertDialog.Builder(this)
          .setTitle(R.string.location_perm_req_title)
          .setMessage(R.string.location_perm_req_message)
          .setPositiveButton(R.string.button_accept, (dialog, which) -> {
            Log.d(TAG, "Requesting COARSE_LOCATION permission");

            ActivityCompat.requestPermissions(this,
              new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
              CODE_COARSE_LOCATION_PERM);
          })
          .setNegativeButton(R.string.button_deny, (dialog, which) -> {
            Log.d(TAG, "COARSE_LOCATION permission denied");

            Toast.makeText(this,
              R.string.location_perm_denied_message,
              Toast.LENGTH_LONG).show();
          })
          .show();

      } else {
        ActivityCompat.requestPermissions(this,
          new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
          CODE_COARSE_LOCATION_PERM);

      }
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    switch (requestCode) {
      case CODE_COARSE_LOCATION_PERM:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
          Log.d(TAG, "COARSE_LOCATION permission denied");
          Toast.makeText(this,
            R.string.location_perm_denied_message,
            Toast.LENGTH_LONG).show();
        }
    }
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
    AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
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
        startActivity(getIntent());
      }
    });
    // show the alert box
    alertbox.show();
  }
}
