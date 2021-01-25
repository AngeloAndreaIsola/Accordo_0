package com.example.mc_project_v00;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
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
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                LatLng yourLatLng = new LatLng((double) latFloat, (double) lonFloat);
                double yourZoom = 7;
                mapboxMap.setCameraPosition(new CameraPosition.Builder()
                        .target(yourLatLng)
                        .zoom(yourZoom)
                        .build());

                mapboxMap.setStyle(Style.TRAFFIC_NIGHT, new Style.OnStyleLoaded() {
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

    // Add the mapView lifecycle to the activity's lifecycle methods
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }



}