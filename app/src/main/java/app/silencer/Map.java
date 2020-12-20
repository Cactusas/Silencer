package app.silencer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputType;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class Map implements OnMapReadyCallback, OnMapLongClickListener {

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private String popup_text = "";
    Database db = new Database(MainActivity.ctx);

    public Map(SupportMapFragment mapFragment_) {
        mapFragment=mapFragment_;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setOnMapLongClickListener(this);

        putGeofences();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        popup(latLng);
    }

    private void popup(LatLng latLng) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.ctx);

        builder.setTitle("Geofence name");

        // Set up the input
        final EditText input = new EditText(MainActivity.ctx);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                popup_text = input.getText().toString();
                map.addMarker(new MarkerOptions().position(latLng).title(popup_text));
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                map.addCircle(new CircleOptions()
                        .center(latLng)
                        .radius(Constants.GEOFENCE_RADIUS_IN_METERS)
                        .strokeColor(Color.RED)
                        .fillColor(0x220000FF)
                        .strokeWidth(5)
                );
                db.insertGeofence(popup_text, latLng);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    private void putGeofences() {
        ArrayList<HashMap<String, String>> geofencesList = db.GetGeofences();
        for(HashMap<String, String> hashMap : geofencesList){
            String name = hashMap.get("name");
            double lat = Double.parseDouble(hashMap.get("latitude"));
            double lng = Double.parseDouble(hashMap.get("longitude"));
            LatLng latLng = new LatLng(lat, lng);
            map.addMarker(new MarkerOptions().position(latLng).title(name));
            map.addCircle(new CircleOptions()
                    .center(latLng)
                    .radius(Constants.GEOFENCE_RADIUS_IN_METERS)
                    .strokeColor(Color.RED)
                    .fillColor(0x220000FF)
                    .strokeWidth(5)
            );
        }
    }
}
