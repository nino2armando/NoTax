package com.example.ninokhodabandeh.notax;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.example.ninokhodabandeh.notax.Ui.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;

public class SplashActivity extends Activity implements
        com.google.android.gms.location.LocationListener,
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    private final int SPLASH_DISPLAY_LENGTH = 5000;

    // Milliseconds per second
    public static final int MILLISECONDS_PER_SECOND = 1000;

    // The update interval
    public static final int UPDATE_INTERVAL_IN_SECONDS = 5;

    // A fast interval ceiling
    public static final int FAST_CEILING_IN_SECONDS = 1;

    // Update interval in milliseconds
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS =
            MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;

    // A fast ceiling of update intervals, used when the app is visible
    public static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS =
            MILLISECONDS_PER_SECOND * FAST_CEILING_IN_SECONDS;

    private LocationClient mLocationClient;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        // todo: fetch user location here
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FAST_INTERVAL_CEILING_IN_MILLISECONDS);


        mLocationClient = new LocationClient(this, this, this);


        //getLocation();
        /**
         * this handler allows us to start the next activity after the given time
         */

/*        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startMainActivity = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(startMainActivity);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Constants.CONNETION_FAILURE_RESOLUTION_REQUEST:
                switch (resultCode){
                    case Activity.RESULT_OK:
                        Log.d(Constants.APPTAG, getString(R.string.resolved));
                        break;
                    default:
                        Log.d(Constants.APPTAG, getString(R.string.no_resolution));
                }
                default:
                    Log.d(Constants.APPTAG, getString(R.string.unknown_activity_request_code, requestCode));
                    break;
        }
    }

    @Override
    protected void onStart() {
        mLocationClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        if(mLocationClient.isConnected()){
            stopPeriodicUpdates();
        }
        mLocationClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onPause() {
        // commit the changes for sharedperfs
        super.onPause();
    }

    private void stopPeriodicUpdates(){
        try{
            mLocationClient.removeLocationUpdates(this);
        }catch (Exception ex){
            Log.e(Constants.APPTAG, ex.getMessage());
        }
    }


    private void startPeriodicUpdates(){
        try{
            mLocationClient.requestLocationUpdates(mLocationRequest, this);
        }catch (Exception ex){
            Log.e(Constants.APPTAG, ex.getMessage());
        }
    }

    private void getLocation(){
        if(isServiceConnected()){
            if(!mLocationClient.isConnected()){
                mLocationClient.connect();
            }
            Location location = mLocationClient.getLastLocation();
            // todo: for now we just display toast
            Toast.makeText(this, getLatLongToString(location), Toast.LENGTH_LONG);
        }
    }

    private static String getLatLongToString(Location currentLocation){
        if(currentLocation != null){
            return String.format("Lat:%1, Long:%2", currentLocation.getLatitude(), currentLocation.getLatitude());
        }else {
            return new String();
        }
    }

    private boolean isServiceConnected(){
        // check if google play Service is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // available
        if(resultCode == ConnectionResult.SUCCESS){
            Log.d(Constants.APPTAG, "Google play service is available");
            return true;
        }else{
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if(dialog != null){
                ErrorFragment errorFragment = new ErrorFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getFragmentManager(), Constants.APPTAG);
            }
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

        //todo: if should do somethig like if updatesRequested based on user settings
        startPeriodicUpdates();
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onLocationChanged(Location location) {
        //todo: do something here
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static class ErrorFragment extends DialogFragment{
        private Dialog mDialog;

        public ErrorFragment(){
            super();
            mDialog = null;
        }

        public void setDialog(Dialog dialog){
            mDialog = dialog;
        }
    }
}
