package com.example.tp2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.R;
import com.example.utils.CommonHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;


public class Exo7Activity extends Activity {
    private MapView mapView;
    private FusedLocationProviderClient fusedLocationClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(), getSharedPreferences("osmdroid", MODE_PRIVATE));
        setContentView(R.layout.activity_tp2_exo7);

        CommonHelper.changeActionbarColor(this, getResources().getColor(R.color.ruby));
        mapView = findViewById(R.id.mapView);

        // Vérifier si la référence est null
        if (mapView != null) {
            mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK);
            mapView.setBuiltInZoomControls(true);
            mapView.setMultiTouchControls(true);

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            getLastLocation();
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    System.out.println("eeeeee");
                    if (location != null) {
                        GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                        Marker startMarker = new Marker(mapView);
                        startMarker.setPosition(startPoint);
                        mapView.getOverlays().add(startMarker);

                        mapView.getController().setZoom(15);
                        mapView.getController().setCenter(startPoint);
                        mapView.getController().animateTo(startPoint);

                        System.out.println("Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
                    } else {
                        System.out.println("Location is null");
                    }
                })
                .addOnFailureListener(this, e -> {
                    System.out.println("zeeze");
                    Toast.makeText(this, "Failed to get location.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Autorisations accordées, obtenir la dernière position
                getLastLocation();
            } else {
                // Autorisations refusées, afficher un message d'erreur
                Toast.makeText(this, "Location permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
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
}
