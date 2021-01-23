package com.example.mc_project_v00;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import org.json.JSONException;

import java.util.List;

/**
 * Use the LocationComponent to easily add a device location "puck" to a Mapbox map.
 */
public class LocationComponentActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener {

    private static final String TAG = "LocationComponentActivity";
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private MapView mapView;
    private Location location;
    private LocationComponent lc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    // Mapbox access token is configured here. This needs to be called either in your application
    // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

    // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_map);
        Button buttonSendPosition = findViewById(R.id.buttonSendLocation);
        findViewById(R.id.buttonSendLocation).setVisibility(View.VISIBLE);
        buttonSendPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendLocation();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                finish();
            }
        });

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


    }

    private void sendLocation() throws JSONException { //TODO: VERIFICA CHE LA POSIZIONE SIA VALIDA
        String channelName = null, sid = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            channelName = extras.getString("nomeCanale");
            sid = extras.getString("sid");
        }

        double lat = location.getLatitude();
        double lon = location.getLongitude();

        ComunicationController comunicationController = new ComunicationController(this);
        comunicationController.addPostPosition(sid, channelName, Double.toString(lat), Double.toString(lon), response -> Log.d(TAG, "Posizione spedita"), error -> Log.d(TAG, "Posizione spedita: " + error));
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        LocationComponentActivity.this.mapboxMap = mapboxMap;


        //new Style.Builder().fromUri("mapbox://styles/mapbox/cjerxnqt3cgvp2rmyuxbeqme7")

        mapboxMap.setStyle(Style.TRAFFIC_NIGHT,
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);


                        LatLng yourLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        double yourZoom = 7;
                        mapboxMap.setCameraPosition(new CameraPosition.Builder()
                                .target(yourLatLng)
                                .zoom(yourZoom)
                                .build());
                    }
                });
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) { //TODO: SE CI METTI TROPPO TEMPO A CONDERE I PERMESSI CRASHA, PROBLEMA DI THREAD
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            lc = locationComponent;

            // Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);



            location = locationComponent.getLastKnownLocation();    //TODO: FARE LA CHIAMATA CHE PRENDE EFFETIVAMENTE L'ULTIMA POSIZIONE E NON SOLO L'ULTIMA CONOSCIUTA

            //TODO: SE CAMBIA LA POSIZINE NELLA SCHERMATA DOVE DOVREBBE ESSERE INVIATA NON MANDA L'ULTIMA

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        //Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            //Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}