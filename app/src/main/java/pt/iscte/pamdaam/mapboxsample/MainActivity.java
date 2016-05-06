package pt.iscte.pamdaam.mapboxsample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.views.MapView;

public class MainActivity extends Activity implements android.location.LocationListener {

    private MapView mapView = null;
    protected LocationManager locationManager;
    public double latitude, longitude;
    protected ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            // podem ser usados outros providers (GPS_PROVIDER, etc.)!!!
        } catch (SecurityException e) {
            Log.i("MAPBOX APP", e.getMessage());
        }

        /*
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Getting location... please wait!");
        dialog.show();
        */

        /** Create a mapView and give it some properties */
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setStyleUrl(Style.MAPBOX_STREETS);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(38.748547784895, -9.155314987341285)) // Sets the center of the map to your current location
                .zoom(11)                                // Sets the zoom
                .build();

        mapView.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mapView.onCreate(savedInstanceState);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null) {
            Log.i("MAPBOX", "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            try {
                locationManager.removeUpdates(this);
            } catch (SecurityException e){
                Log.i("MAPBOX APP", e.getMessage());
            }

            //dialog.dismiss();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("MAPBOX", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("MAPBOX","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("MAPBOX","status");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause()  {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
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
