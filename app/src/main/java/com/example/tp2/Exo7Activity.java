package com.example.tp2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;


public class Exo7Activity extends Activity {
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(), getSharedPreferences("osmdroid", MODE_PRIVATE));
        setContentView(R.layout.activity_tp2_exo7);

        // Récupérer la référence au MapView
        mapView = findViewById(R.id.mapView);

        // Vérifier si la référence est null
        if (mapView != null) {
            mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK);
            mapView.setBuiltInZoomControls(true);
            mapView.setMultiTouchControls(true);

            // Ajouter un marqueur pour la position de l'utilisateur
            GeoPoint startPoint = new GeoPoint(48.8583, 2.2944); // Exemple: Coordonnées de la Tour Eiffel
            Marker startMarker = new Marker(mapView);
            startMarker.setPosition(startPoint);
            mapView.getOverlays().add(startMarker);

            mapView.getController().setZoom(15);
            mapView.getController().setCenter(startPoint);

            // Associer un gestionnaire d'événements de clic au marqueur
            startMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker, MapView mapView) {
                    // Créer et afficher un AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(Exo7Activity.this);
                    builder.setTitle("Info");
                    builder.setMessage(getResources().getString(R.string.position));
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Action à effectuer lorsque l'utilisateur clique sur "OK"
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }
            });
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
