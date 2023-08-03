// Import required libraries
package com.android.aquadelivery;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapFragment extends Fragment {
    //This variable is of type GoogleMap and is used to interact with the Google Maps API.
    private GoogleMap mMap;

   //user's current location using the Fused Location Provider API.
    private FusedLocationProviderClient mFusedLocationClient;

    //LatLng and represents the current location of the user on the map.
    private LatLng mCurrentLocation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());


        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_map,container,false);
        SupportMapFragment supportMapFragment=(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap=googleMap;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(6.9185776644458095, 79.88140874020378), 12));
                mMap.addMarker(new MarkerOptions().position(new LatLng(6.9185776644458095, 79.88140874020378)).title("Cinnamon Citadel"));
                if (ContextCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    // Get the user's current location
                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location != null) {
                                        mCurrentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                        // Move the camera to the user's current location
                                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLocation, 15), 2000, null);
                                    }
                                }
                            });
                } else {
                    // Request permission to access the user's location
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                }
                mMap.getUiSettings().setZoomControlsEnabled(true);
            }
        });
        return view;
    }



}