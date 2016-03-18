package no.woact.pg4600.assignment2.ronesp13;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

import no.woact.pg4600.assignment2.ronesp13.models.Marker;

public class MapsActivity extends FragmentActivity {

    private Context context;
    private GoogleMap googleMap;
    private static final String LOCATION_TAG = "location";
    private static final String LATITUDE_TAG = "lat";
    private static final String LONGITUDE_TAG = "long";

    private Marker[] markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        context = this;
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (googleMap == null) {
            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (googleMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        new CityTask().execute();
    }

    private class CityTask extends AsyncTask<Void, Void, Marker[]> {

        private static final String CITY_URL = "http://www.abztracta.com/mjosbyene.json";

        @Override
        protected Marker[] doInBackground(Void... params) {
            try {
                String jsonString = readUrl();
                markers = parseJson(jsonString);
            } catch (IOException e) {
                Log.e("error", "User might not have an internet connection.");
                markers = new Marker[0];
            } catch (JSONException e) {
                Log.e("error", "Parsing of JSON failed. Changes to external host?");
                markers = new Marker[0];
            }
            return markers;
        }

        @Override
        protected void onPostExecute(Marker[] markers) {
            placeMarkers(markers);
            positionCamera(markers);
        }

        private void placeMarkers(Marker[] markers) {
            for (Marker marker : markers) {
                googleMap.addMarker(new MarkerOptions().position(new LatLng(marker.getLatitude(), marker.getLongitude())).title(marker.getLocation()));
            }
        }

        /**
         * @see <a href="http://stackoverflow.com/questions/14828217/android-map-v2-zoom-to-show-all-the-markers">http://stackoverflow.com/questions/14828217/android-map-v2-zoom-to-show-all-the-markers</a>
         */
        private void positionCamera(Marker[] markers) {
            if (markers.length == 0) {
                Toast.makeText(context, "Are you connected to the internet?", Toast.LENGTH_LONG).show();
                return;
            }
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markers) {
                builder.include(new LatLng(marker.getLatitude(), marker.getLongitude()));
            }
            LatLngBounds bounds = builder.build();
            int padding = 50;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            googleMap.moveCamera(cameraUpdate);
            googleMap.animateCamera(cameraUpdate);
        }

        private String readUrl() throws IOException {
            URL url = new URL(CITY_URL);
            Scanner scanner = new Scanner(new InputStreamReader(url.openStream()));
            String content = "";
            while (scanner.hasNext()) {
                content += scanner.nextLine() + "\n";
            }
            return content;
        }

        private Marker[] parseJson(String json) throws JSONException {
            JSONArray cities = new JSONArray(json);
            Marker[] markers = new Marker[cities.length()];
            for (int i = 0; i < cities.length(); i++) {
                JSONObject object = cities.getJSONObject(i);
                markers[i] = new Marker(object.getString(LOCATION_TAG), object.getDouble(LATITUDE_TAG), object.getDouble(LONGITUDE_TAG));
            }
            return markers;
        }
    }
}
