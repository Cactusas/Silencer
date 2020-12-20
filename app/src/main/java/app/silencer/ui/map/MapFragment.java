package app.silencer.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.SupportMapFragment;

import app.silencer.R;

import com.google.android.gms.maps.GoogleMap;

import app.silencer.Map;

public class MapFragment extends Fragment {

    private GoogleMap mMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().
                findFragmentById(R.id.mapView);

        Map map = new Map(mapFragment);

        return root;
    }
}