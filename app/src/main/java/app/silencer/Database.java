package app.silencer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

public class Database extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "geofencesdb";
    private static final String TABLE_GEOFENCES = "geofences";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LNG = "longitude";

    public Database(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_GEOFENCES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_LAT + " DOUBLE,"
                + KEY_LNG + " DOUBLE"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GEOFENCES);
        // Create tables again
        onCreate(db);
    }

    public void insertGeofence(String name, LatLng latLng){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_LAT, latLng.latitude);
        cValues.put(KEY_LNG, latLng.longitude);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_GEOFENCES,null, cValues);
        db.close();
    }

    public ArrayList<HashMap<String, String>> GetGeofences(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, latitude, longitude FROM "+ TABLE_GEOFENCES;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("latitude",cursor.getString(cursor.getColumnIndex(KEY_LAT)));
            user.put("longitude",cursor.getString(cursor.getColumnIndex(KEY_LNG)));
            userList.add(user);
        }
        return  userList;
    }
}