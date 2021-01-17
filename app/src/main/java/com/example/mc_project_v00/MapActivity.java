package com.example.mc_project_v00;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;



public class MapActivity extends AppCompatActivity {
    private MapView mapView;
    private  String latString, lonString;
    private float latFloat, lonFloat;
    public MarkerOptions markerOptions;

    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";

    private static final String TAG = "Map Activity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_map);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            latString = extras.getString("lat");
            lonString = extras.getString("lon");

            latFloat = Float.parseFloat(latString);
            lonFloat = Float.parseFloat(lonString);
        }
        Log.d(TAG, "Lat float: " + Float.toString(latFloat) + " Lon float: " + Float.toString(lonFloat));
        Log.d(TAG, "LAT string: " + latString + " LON string: " + lonString);



        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);



        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {



                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        Log.d(TAG, "Map created and style loaded");
                        //MAP IS SET UP AND THE STYLE HAS LOADED. NOEW YOU CAN ADD DATA OR MAKE OTHER MAP ADJUSTMENTS

                        markerOptions = new MarkerOptions();
                        markerOptions.title("posizione condivisa");
                        markerOptions.position(new LatLng(latFloat, lonFloat));
                        mapboxMap.addMarker(markerOptions);
                    }
                });
            }
        });
    }
}