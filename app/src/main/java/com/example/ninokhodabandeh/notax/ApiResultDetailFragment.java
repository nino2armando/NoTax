package com.example.ninokhodabandeh.notax;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ninokhodabandeh.notax.Models.ApiResultModel;
import com.example.ninokhodabandeh.notax.Ui.Constants;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by nino.khodabandeh on 9/16/2014.
 */
public class ApiResultDetailFragment extends Fragment {
    MapView mapView;
    GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apiresultdetail, container, false);

        Bundle args = getArguments();
        ApiResultModel passedItem = args.getParcelable(Constants.SELECTED_LIST_ITEM);
        String markerTitle = passedItem.getContent();

        LatLng tempPosition = new LatLng(49.2500, -123.1333);

        MapsInitializer.initialize(getActivity());

        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity())){
            case ConnectionResult.SUCCESS:
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                mapView = (MapView) view.findViewById(R.id.map);
                mapView.onCreate(savedInstanceState);

                if(mapView != null){
                    mapView.setClickable(false);
                    map = mapView.getMap();
                    map.getUiSettings().setMyLocationButtonEnabled(true);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(tempPosition, 12);
                    map.animateCamera(cameraUpdate);
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(tempPosition)
                            .draggable(false)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .title(markerTitle);


                    map.addMarker(markerOptions).showInfoWindow();

                }
                break;
            case ConnectionResult.SERVICE_MISSING:
                Toast.makeText(getActivity(), "Service Missing", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                Toast.makeText(getActivity(), "Update Required", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getActivity(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void onResume() {
        if(mapView != null)
            mapView.onResume();

        super.onResume();
    }

    @Override
    public void onDestroy() {
        if(mapView != null)
            mapView.onDestroy();

        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        if(mapView != null)
            mapView.onLowMemory();

        super.onLowMemory();
    }
}
