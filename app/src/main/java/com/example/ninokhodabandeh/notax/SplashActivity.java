package com.example.ninokhodabandeh.notax;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ninokhodabandeh.notax.Models.SimpleLocation;
import com.example.ninokhodabandeh.notax.Ui.Constants;
import com.example.ninokhodabandeh.notax.util.LocationUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SplashActivity extends Activity implements
        LocationListener,
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    private LocationRequest mLocationRequest;
    private LocationClient mLocationClient;
    private ProgressBar mActivityIndicator;

    private Location mLocation;

    private SharedPreferences mSharedPrefs;
    private SharedPreferences.Editor mSharedPrefsEditor;

    // we should get this from the user settings
    private boolean mUpdatedRequested = true;

    private Gson mGson;

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        mSharedPrefs = getSharedPreferences(LocationUtils.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mSharedPrefsEditor = mSharedPrefs.edit();
        mGson = new Gson();

        mLocationRequest = LocationRequest.create();

        mLocationRequest.setInterval(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setFastestInterval(LocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);

        mLocationClient = new LocationClient(this, this, this);

        mActivityIndicator = (ProgressBar) findViewById(R.id.location_progress);
        try {
            mLocationClient.connect();
        }catch (Exception ex){
            Log.d(LocationUtils.APPTAG, ex.getMessage());
        }

        /**
         * this handler allows us to start the next activity after the given time
         */

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startMainActivity = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(startMainActivity);

                String jsonLocation = mGson.toJson(new SimpleLocation(mLocation.getLatitude(), mLocation.getLongitude()));
                mSharedPrefsEditor.putString(Constants.RETRIEVED_LOCATION, jsonLocation);
                mSharedPrefsEditor.commit();

                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mLocationClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mSharedPrefs.contains(LocationUtils.KEY_UPDATES_REQUESTED)){
            mUpdatedRequested = mSharedPrefs.getBoolean(LocationUtils.KEY_UPDATES_REQUESTED, false);
        }else{
            mSharedPrefsEditor.putBoolean(LocationUtils.KEY_UPDATES_REQUESTED, false);
            mSharedPrefsEditor.commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSharedPrefsEditor.putBoolean(LocationUtils.KEY_UPDATES_REQUESTED, mUpdatedRequested);
        mSharedPrefsEditor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mLocationClient.isConnected()){
            stopPeriodicUpdates();
        }
        mLocationClient.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case LocationUtils.CONNETION_FAILURE_RESOLUTION_REQUEST:
                switch (resultCode){
                    case Activity.RESULT_OK:
                        Log.d(LocationUtils.APPTAG, getString(R.string.resolved));
                        break;
                    default:
                        Log.d(LocationUtils.APPTAG, getString(R.string.no_resolution));
                }
                break;
            default:
                Log.d(LocationUtils.APPTAG, getString(R.string.unknown_activity_request_code, requestCode));
                break;
        }
    }


    private boolean serviceConnected(){
        // check google playservice is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // available
        if(ConnectionResult.SUCCESS == resultCode){
            Log.d(LocationUtils.APPTAG, getString(R.string.play_service_available));
            return true;
        }else  {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if(dialog != null){
                ErrorFragment errorFragment = new ErrorFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getFragmentManager(), LocationUtils.APPTAG);
            }
            return false;
        }
    }

    private Location getLocation(){
        Location location = null;
        if(serviceConnected()){
            location = mLocationClient.getLastLocation();
        }
        return location;
    }

    public void getAddress(){
        if(!Geocoder.isPresent()){
            Toast.makeText(this, R.string.no_geoCoder_available, Toast.LENGTH_LONG).show();
            return;
        }

        if(serviceConnected()){
            Location location = mLocationClient.getLastLocation();
            mActivityIndicator.setVisibility(View.VISIBLE);
            (new GetAddressTask(this)).execute(location);
        }
    }

    private void startPeriodicUpdates(){
        try{
            mLocationClient.requestLocationUpdates(mLocationRequest, this);
        }catch(Exception ex){
            Log.e(LocationUtils.APPTAG, ex.getMessage());
        }
    }

    public void stopPeriodicUpdates(){
        try{
            mLocationClient.removeLocationUpdates(this);
        }catch (Exception ex){
            Log.e(LocationUtils.APPTAG, ex.getMessage());
        }
    }

    private void showErrorDialog(int errorCode){
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode, this, LocationUtils.CONNETION_FAILURE_RESOLUTION_REQUEST);

        if(errorDialog != null){
            ErrorFragment errorFragment = new ErrorFragment();
            errorFragment.setDialog(errorDialog);
            errorFragment.show(getFragmentManager(), LocationUtils.APPTAG);
        }
    }

    public static class ErrorFragment extends DialogFragment {

        private Dialog _dialog;

        public ErrorFragment(){
            super();
            _dialog = null;
        }

        public void setDialog(Dialog dialog){
            _dialog =  dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return _dialog;
        }
    }

    // com.google.android.gms.location.LocationListener METHODS
    @Override
    public void onLocationChanged(Location location) {
        // todo: do location changed stuff here
        mLocation = location;
    }

    // GooglePlayServicesClient.ConnectionCallbacks METHODS
    @Override
    public void onConnected(Bundle bundle) {
        // todo: if update is required
        // then do the periodic updates here
        mLocation = getLocation();

        if(mUpdatedRequested){
            startPeriodicUpdates();
        }

        getAddress();
    }

    @Override
    public void onDisconnected() {
        // todo: maybe log or notify
    }

    // GooglePlayServicesClient.OnConnectionFailedListener METHODS
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if(connectionResult.hasResolution()){
            try{
                connectionResult.startResolutionForResult(this, LocationUtils.CONNETION_FAILURE_RESOLUTION_REQUEST);
            }catch (IntentSender.SendIntentException ex){
                ex.printStackTrace();
            }

        }else {
            showErrorDialog(connectionResult.getErrorCode());
        }
    }

    protected class GetAddressTask extends AsyncTask<Location, Void, String> {

        Context mContext;

        public GetAddressTask(Context context)
        {
            super();
            mContext = context;
        }

        @Override
        protected String doInBackground(Location... locations) {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            Location location = locations[0];
            List<Address> addresses = null;

            try{
                addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
            }catch (IOException ex)
            {
                Log.e(LocationUtils.APPTAG, getString(R.string.IO_Exception_getFromLocation));
                ex.printStackTrace();
                return (getString(R.string.IO_Exception_getFromLocation));
            }catch (IllegalArgumentException ex2)
            {
                String errorString = getString(R.string.illegal_argument_exception, location.getLatitude(), location.getLongitude());
                Log.e(LocationUtils.APPTAG, errorString);
                ex2.printStackTrace();
                return errorString;
            }

            if(addresses != null && addresses.size() > 0)
            {
                Address address = addresses.get(0);

                String addressText = getString(R.string.address_output_string,
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0): "",
                        address.getLocality(), // locality is the city
                        address.getCountryName());
                return addressText;
            }else
            {
                return getString(R.string.no_address_found);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            mActivityIndicator.setVisibility(View.GONE);
        }
    }

}
