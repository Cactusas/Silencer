package app.silencer.ui.geofences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

import app.silencer.Database;
import app.silencer.MainActivity;
import app.silencer.R;



public class GeofencesFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_geofences, container, false);

        Database db = new Database(MainActivity.ctx);

        ArrayList<HashMap<String, String>> geofencesList = db.GetGeofences();
        ListView lv = (ListView) root.findViewById(R.id.user_list);
        ListAdapter adapter = new SimpleAdapter(MainActivity.ctx, geofencesList, R.layout.list_row,new String[]{"name","latitude","longitude"}, new int[]{R.id.name, R.id.latitude, R.id.longitude});
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });

        return root;
    }
}