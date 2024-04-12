package com.example.soen357_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DogParksNearMe extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_parks_near_me);
        listView = findViewById(R.id.dogParkListView);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyCnQmKCovaW8yVQgNuXBU9LfirxbThA-S0");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Static Montreal
        fetchDogParks(45.49719, -73.57907);
        onDogParkItemClick();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        LatLng montreal = new LatLng(45.49719, -73.57907);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(montreal, 15));
    }

    public void onImageClicked(View view) {
        Intent intent = new Intent(DogParksNearMe.this, Dashboard.class);
        startActivity(intent);
    }

    // REST API GET Request to retrieve dog parks near location provided with a radius of 10000
    private void fetchDogParks(double latitude, double longitude) {
        OkHttpClient client = new OkHttpClient();
        String apiKey = "AIzaSyCnQmKCovaW8yVQgNuXBU9LfirxbThA-S0";
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude + "," + longitude + "&radius=10000&type=park&keyword=dog&key=" + apiKey;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
                Log.d("F", "fetchDogParks: FAIL");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();

                    // use runOnUiThread as specified in the Android documentation
                    runOnUiThread(() -> {
                        List<DogPark> dogParks = parseParkNamesFromResponse(responseData);
                        updateListView(dogParks);
                        updateMapMarkers(dogParks);
                    });
                }
            }
        });
    }

    private List<DogPark> parseParkNamesFromResponse(String response) {
        List<DogPark> dogParks = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            Log.d("API Status", status);
            JSONArray results = jsonObject.getJSONArray("results");
            Log.d("tempCheck", results.toString());

            for (int i = 0; i < results.length(); i++) {
                JSONObject dogPark = results.getJSONObject(i);
                String name = dogPark.getString("name");
                if (name.contains("dog park") || name.contains("Dog park")) {
                    name = name.replace("dog park", "").trim();
                }
                String address = dogPark.getString("vicinity");

                JSONObject location = dogPark.getJSONObject("geometry").getJSONObject("location");
                double latitude = location.getDouble("lat");
                double longitude = location.getDouble("lng");
                dogParks.add(new DogPark(name, address, latitude, longitude));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dogParks;
    }

    // Make map move to dog park that is pressed in list
    private void onDogParkItemClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DogPark dogPark = (DogPark) parent.getItemAtPosition(position);
                LatLng location = new LatLng(dogPark.getLatitude(), dogPark.getLongitude());
                if (gMap != null) {
                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                }
            }
        });
    }


    // ListView for dog parks based on dog parks received
    private void updateListView(List<DogPark> parkNames) {
        DogParkAdapter adapter = new DogParkAdapter(this, parkNames);
        listView.setAdapter(adapter);
    }

    // Place map markers for dog parks
    private void updateMapMarkers(List<DogPark> dogParks) {
        for (DogPark dogPark : dogParks) {
            LatLng dogParkLocation = new LatLng(dogPark.getLatitude(), dogPark.getLongitude());
            gMap.addMarker(new MarkerOptions()
                    .position(dogParkLocation)
                    .title(dogPark.getName())
                    .snippet(dogPark.getAddress()));
        }
    }

}